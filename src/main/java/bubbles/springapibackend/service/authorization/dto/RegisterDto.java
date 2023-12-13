package bubbles.springapibackend.service.authorization.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email, @NotNull String password) {}
