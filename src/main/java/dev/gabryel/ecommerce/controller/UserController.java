package dev.gabryel.ecommerce.controller;

import dev.gabryel.ecommerce.dto.user.request.UserLoginRequest;
import dev.gabryel.ecommerce.dto.user.request.UserRegisterRequest;
import dev.gabryel.ecommerce.dto.user.response.UserLoginResponse;
import dev.gabryel.ecommerce.dto.user.response.UserRegisterResponse;
import dev.gabryel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register-user")
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody @Valid UserRegisterRequest userRequest) {
        UserRegisterResponse userResponse = new UserRegisterResponse(userService.userRegister(userRequest, 2));
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody @Valid UserLoginRequest userRequest) {
        UserLoginResponse token = new UserLoginResponse(userService.userLogin(userRequest));
        return ResponseEntity.ok(token);
    }
}
