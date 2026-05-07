package dev.gabryel.ecommerce.controller;

import dev.gabryel.ecommerce.config.JWTUserData;
import dev.gabryel.ecommerce.dto.user.request.*;
import dev.gabryel.ecommerce.dto.user.response.*;
import dev.gabryel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list-all")
    public ResponseEntity<List<UserListResponse>> userListAll() {
        List<UserListResponse> userListResponses = userService.userListAll();
        return ResponseEntity.ok(userListResponses);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-email")
    public ResponseEntity<UserUpdateEmailResponse> userUpdate(@AuthenticationPrincipal JWTUserData userData,
                                                              @RequestBody @Valid UserUpdateEmailRequest userRequest) {
        UserUpdateEmailResponse response = new UserUpdateEmailResponse(userService.userUpdate(userData, userRequest));
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-nameandpass")
    public ResponseEntity<UserUpdateNameAndPassResponse> userUpdateNameAndPass(@AuthenticationPrincipal JWTUserData userData,
                                                                               @RequestBody @Valid UserUpdateNameAndPassRequest userRequest) {
        UserUpdateNameAndPassResponse userUpdateNameAndPass = userService.userUpdateNameAndPass(userData, userRequest);
        return ResponseEntity.ok(userUpdateNameAndPass);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> userDeleteById(@PathVariable("id") UUID id) {
        userService.userDeleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    public ResponseEntity<String> userDelete(@AuthenticationPrincipal JWTUserData userData,
                                             @RequestBody @Valid UserDeleteRequest userDelete) {
        userService.userDelete(userData, userDelete);
        return ResponseEntity.ok("Successfully deleted");
    }
}
