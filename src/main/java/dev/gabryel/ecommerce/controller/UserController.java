package dev.gabryel.ecommerce.controller;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.user.request.UserLoginRequest;
import dev.gabryel.ecommerce.dto.user.request.UserRegisterRequest;
import dev.gabryel.ecommerce.dto.user.request.UserUpdateEmailRequest;
import dev.gabryel.ecommerce.dto.user.response.UserLoginResponse;
import dev.gabryel.ecommerce.dto.user.response.UserRegisterResponse;
import dev.gabryel.ecommerce.dto.user.response.UserUpdateEmailResponse;
import dev.gabryel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody @Valid UserRegisterRequest userRequest) {
        UserRegisterResponse userResponse = new UserRegisterResponse(userService.userRegister(userRequest, 2));
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<UserRegisterResponse> adminRegister(@RequestBody @Valid UserRegisterRequest userRequest) {
        UserRegisterResponse userResponse = new UserRegisterResponse(userService.userRegister(userRequest, 1));
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody @Valid UserLoginRequest userRequest) {
        UserLoginResponse token = new UserLoginResponse(userService.userLogin(userRequest));
        return ResponseEntity.ok(token);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-email")
    public ResponseEntity<UserUpdateEmailResponse> userUpdate(@AuthenticationPrincipal JWTUserData userData,
                                                              @RequestBody @Valid UserUpdateEmailRequest userRequest) {
        UserUpdateEmailResponse response = new UserUpdateEmailResponse(userService.userUpdate(userData, userRequest));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> userDeleteById(@PathVariable("id") UUID id) {
        userService.userDeleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
