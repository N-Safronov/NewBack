package com.example.demo.service;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.CustomSuccessResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CustomSuccessResponse<AuthUserDto> register(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);

            var jwtToken = jwtService.generateToken(user);

            AuthUserDto authUserDto = AuthUserDto.builder()
                    .avatar("avatar_url")
                    .email(user.getEmail())
                    .id(user.getId().toString())
                    .name(user.getFirstname() + " " + user.getLastname())
                    .role(user.getRole().toString())
                    .token(jwtToken)
                    .build();

            return CustomSuccessResponse.<AuthUserDto>builder()
                    .data(authUserDto)
                    .statusCode(200)
                    .success(true)
                    .build();
        }
        catch (Exception e){
            return CustomSuccessResponse.<AuthUserDto>builder()
                    .statusCode(500)
                    .success(false)
                    .build();
        }
    }

    public CustomSuccessResponse<AuthUserDto> login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);

            AuthUserDto authUserDto = AuthUserDto.builder()
                    .avatar("avatar_url")
                    .email(user.getEmail())
                    .id(user.getId().toString())
                    .name(user.getFirstname() + " " + user.getLastname())
                    .role(user.getRole().toString())
                    .token(jwtToken)
                    .build();

            return CustomSuccessResponse.<AuthUserDto>builder()
                    .data(authUserDto)
                    .statusCode(200)
                    .success(true)
                    .build();
        }
        catch (Exception e){
            return CustomSuccessResponse.<AuthUserDto>builder()
                    .statusCode(500)
                    .success(false)
                    .build();
        }
    }

}
