package org.example.taskmanagementapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementapp.exception.IncorrectPasswordValidationException;
import org.example.taskmanagementapp.exception.UserNotFoundException;
import org.example.taskmanagementapp.exception.UserRegistrationException;
import org.example.taskmanagementapp.mapper.UserMapper;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.model.enums.UserRole;
import org.example.taskmanagementapp.model.request.AuthenticationRequest;
import org.example.taskmanagementapp.model.request.RegistrationRequest;
import org.example.taskmanagementapp.model.response.AuthenticationResponse;
import org.example.taskmanagementapp.model.response.RegistrationResponse;
import org.example.taskmanagementapp.repository.UserRepository;
import org.example.taskmanagementapp.service.AuthService;
import org.example.taskmanagementapp.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException(String.format("User with email: %s not found", request.getEmail())));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = JwtTokenUtil.generateToken(user.getEmail(), user.getRole().name());
            return AuthenticationResponse.builder()
                    .accessToken(token)
                    .issuedAt(JwtTokenUtil.getIssuedAtFromToken(token))
                    .expiresAt(JwtTokenUtil.getExpiresAtFromToken(token))
                    .build();
        } else {
            throw new IncorrectPasswordValidationException("Entered password is incorrect");
        }
    }

    @Transactional
    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegistrationException(String.format("User with email: %s already exists", request.getEmail()));
        }

        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(newUser);

        return userMapper.toRegistrationResponse(savedUser);
    }
}
