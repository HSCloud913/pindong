package kr.pindong.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RefreshRequest(@NotNull UUID userId, @NotBlank String refreshToken) {
}
