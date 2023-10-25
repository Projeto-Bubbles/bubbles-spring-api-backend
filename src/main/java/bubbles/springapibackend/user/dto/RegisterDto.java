package bubbles.springapibackend.user.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email, @NotNull String password) {}
