package com.proyecto.time.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("refresh_token")
    String refreshToken,
    @JsonProperty("user_id")
    Long userId,
    @JsonProperty("username")
    String username
) { 
    
}
