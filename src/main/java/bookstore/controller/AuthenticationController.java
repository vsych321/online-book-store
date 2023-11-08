package bookstore.controller;

import bookstore.dto.user.UserLoginRequestDto;
import bookstore.dto.user.UserLoginResponseDto;
import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.security.AuthenticationService;
import bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoints for managing authentication")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "Login to website",
            description = "Allow registered users to login into website")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Operation(summary = "Register in the website",
            description = "Allow users to register into website")
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}
