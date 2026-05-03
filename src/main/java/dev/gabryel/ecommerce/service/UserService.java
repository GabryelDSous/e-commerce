package dev.gabryel.ecommerce.service;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.config.TokenConfig;
import dev.gabryel.ecommerce.dto.user.request.UserDeleteRequest;
import dev.gabryel.ecommerce.dto.user.request.UserLoginRequest;
import dev.gabryel.ecommerce.dto.user.request.UserRegisterRequest;
import dev.gabryel.ecommerce.dto.user.request.UserUpdateEmailRequest;
import dev.gabryel.ecommerce.exception.UserException;
import dev.gabryel.ecommerce.mapper.UserMapper;
import dev.gabryel.ecommerce.model.UserModel;
import dev.gabryel.ecommerce.model.enums.UserRoles;
import dev.gabryel.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenConfig tokenConfig, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public String userRegister(UserRegisterRequest userRequest, int accountType) {
        UserModel userModel = UserMapper.toUserModel(userRequest);
        userModel.setPassword(passwordEncoder.encode(userRequest.password()));
        switch (accountType) {
            case 1 -> userModel.setRole(UserRoles.ADMIN);
            case 2 -> userModel.setRole(UserRoles.USER);
        }
        userRepository.save(userModel);
        return userModel.getEmail();
    }

    public String userLogin(UserLoginRequest userRequest) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                userRequest.email(), userRequest.password()
        );
        UserModel userModel = (UserModel) authenticationManager.authenticate(userAndPass).getPrincipal();
        return tokenConfig.generateToken(userModel);
    }

    @Transactional
    public String userUpdate(JWTUserData userData, UserUpdateEmailRequest userRequest) {
        UserModel userModel = userRepository.findByEmail(userData.email())
                .orElseThrow(() -> new UserException("User logged does not found", HttpStatus.NOT_FOUND.value()));
        if (!passwordEncoder.matches(userRequest.password(), userModel.getPassword()))
            throw new BadCredentialsException("Wrong password");
        userModel.setEmail(userRequest.newEmail());
        return userModel.getEmail();
    }

    public void userDeleteById(UUID id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User ID does not found", HttpStatus.NOT_FOUND.value()));
        userRepository.delete(userModel);
    }

    public void userDelete(JWTUserData userData, UserDeleteRequest userRequest) {
        UserModel userModel = userRepository.findByEmail(userData.email())
                .orElseThrow(() -> new UserException("User logged does not found", HttpStatus.NOT_FOUND.value()));
        if (!passwordEncoder.matches(userRequest.password(), userModel.getPassword()))
            throw new BadCredentialsException("Wrong password");
        userRepository.delete(userModel);
    }
}
