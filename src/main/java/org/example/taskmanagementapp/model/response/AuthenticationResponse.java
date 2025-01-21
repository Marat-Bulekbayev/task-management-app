package org.example.taskmanagementapp.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    private Date issuedAt;
    private Date expiresAt;
}
