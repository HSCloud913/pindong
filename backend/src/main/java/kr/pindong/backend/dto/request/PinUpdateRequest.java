package kr.pindong.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PinUpdateRequest(
        @NotBlank @Size(max = 50) String category,
        @NotBlank @Size(max = 200) String title,
        @NotBlank String content
) {}
