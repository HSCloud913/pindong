package kr.pindong.backend.dto.response;

import kr.pindong.backend.domain.pin.Pin;

import java.time.LocalDateTime;
import java.util.UUID;

public record PinResponse(
        UUID id,
        String category,
        String title,
        String content,
        double latitude,
        double longitude,
        String status,
        int viewCount,
        String authorNickname,
        String neighborhoodName,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
    public static PinResponse from(Pin pin) {
        return new PinResponse(
                pin.getId(),
                pin.getCategory(),
                pin.getTitle(),
                pin.getContent(),
                pin.getLocation().getY(),
                pin.getLocation().getX(),
                pin.getStatus().name(),
                pin.getViewCount(),
                pin.getUser().getNickname(),
                pin.getNeighborhood() != null ? pin.getNeighborhood().getName() : null,
                pin.getCreatedAt(),
                pin.getExpiresAt()
        );
    }
}
