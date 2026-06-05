package kr.pindong.backend.domain.reaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
    Optional<Reaction> findByPinIdAndUserIdAndType(UUID pinId, UUID userId, String type);
    boolean existsByPinIdAndUserIdAndType(UUID pinId, UUID userId, String type);
}
