package kr.pindong.backend.domain.pin;

import kr.pindong.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PinRepository extends JpaRepository<Pin, UUID> {

    List<Pin> findByUserAndStatusNotOrderByCreatedAtDesc(User user, PinStatus status);

    @Query(value = """
              SELECT * FROM pins
              WHERE ST_DWithin(location::geography, ST_MakePoint(:lng, :lat)::geography, :radius)
              AND status = 'active'
              ORDER BY created_at DESC
              """, nativeQuery = true)
    List<Pin> findNearby(@Param("lat") double lat,
                         @Param("lng") double lng,
                         @Param("radius") double radius);
}
