package com.asier.SistemaReservas.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access-token") String accessToken,
        @JsonProperty("refresh-token") String refreshToken
) {
}
