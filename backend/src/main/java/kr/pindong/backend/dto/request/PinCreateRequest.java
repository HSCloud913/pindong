package kr.pindong.backend.dto.request;

import jakarta.validation.constraints.*;

public record PinCreateRequest(
        @NotBlank @Size(max = 50) String category,
        @NotBlank @Size(max = 200) String title,
        @NotBlank String content,
        @DecimalMin("-90.0") @DecimalMax("90.0") double latitude,
        @DecimalMin("-180.0") @DecimalMax("180.0") double longitude
) {}

