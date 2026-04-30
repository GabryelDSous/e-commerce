package dev.gabryel.ecommerce.service;

import dev.gabryel.ecommerce.dto.user.request.UserRegisterRequest;
import dev.gabryel.ecommerce.exception.UserException;
import dev.gabryel.ecommerce.mapper.UserMapper;
import dev.gabryel.ecommerce.model.UserModel;
import dev.gabryel.ecommerce.model.enums.UserRoles;
import dev.gabryel.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String userRegister(UserRegisterRequest userRequest, int accountType) {
        UserModel userModel = UserMapper.toUserModel(userRequest);
        try {
            userModel.setPassword(passwordEncoder.encode(userRequest.password()));
            switch (accountType) {
                case 1 -> userModel.setRole(UserRoles.ADMIN);
                case 2 -> userModel.setRole(UserRoles.USER);
            }
            userRepository.save(userModel);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause() instanceof SQLException sqlEx &&
            "23505".equals(sqlEx.getSQLState())) {
                throw new UserException("User already registered", HttpStatus.CONFLICT.value());
            }
        }
        return userModel.getEmail();
    }
}
