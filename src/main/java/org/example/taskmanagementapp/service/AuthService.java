package org.example.taskmanagementapp.service;

import org.example.taskmanagementapp.model.request.AuthenticationRequest;
import org.example.taskmanagementapp.model.request.RegistrationRequest;
import org.example.taskmanagementapp.model.response.AuthenticationResponse;
import org.example.taskmanagementapp.model.response.RegistrationResponse;

public interface AuthService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    RegistrationResponse register(RegistrationRequest request);
}
