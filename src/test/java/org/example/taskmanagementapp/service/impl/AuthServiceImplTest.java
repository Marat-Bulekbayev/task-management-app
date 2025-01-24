package org.example.taskmanagementapp.service.impl;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private AuthenticationRequest authenticationRequest;
    private RegistrationRequest registrationRequest;

    @BeforeEach
    public void setUp() {
        authenticationRequest = new AuthenticationRequest("user@email.com", "passowrd");
        registrationRequest = new RegistrationRequest("User", "User", "user@email.com", "password");
        user = User.builder()
                .id(1L)
                .email("user@email.com")
                .firstName("User")
                .lastName("User")
                .password("password")
                .role(UserRole.ADMIN)
                .build();
    }

    @Test
    void testAuthenticate_whenSuccess() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        AuthenticationResponse response = authService.authenticate(authenticationRequest);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getAccessToken());

        verify(userRepository).findByEmail(any());
        verify(passwordEncoder).matches(any(), any());
    }

    @Test
    void testAuthenticate_whenUserNotFoundException() {
        when(userRepository.findByEmail(any())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> authService.authenticate(authenticationRequest));
    }

    @Test
    void testAuthenticate_whenIncorrectPasswordValidationException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(IncorrectPasswordValidationException.class, () -> authService.authenticate(authenticationRequest));
    }

    @Test
    void testRegister_whenSuccess() {
        RegistrationResponse expectedResponse = RegistrationResponse.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .email("user@email.com")
                .build();

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userMapper.toUserEntity(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("password");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toRegistrationResponse(any())).thenReturn(expectedResponse);

        RegistrationResponse response = authService.register(registrationRequest);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(userRepository).existsByEmail(any());
        verify(userMapper).toUserEntity(any());
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(any());
        verify(userMapper).toRegistrationResponse(any());
    }

    @Test
    void testRegister_whenUserRegistrationException() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(UserRegistrationException.class, () -> authService.register(registrationRequest));

        verify(userRepository).existsByEmail(any());
        verify(userMapper, never()).toUserEntity(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toRegistrationResponse(any());
    }
}
