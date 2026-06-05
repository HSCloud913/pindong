package kr.pindong.backend.domain.neighborhood;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "neighborhoods")
@Getter
@NoArgsConstructor
public class Neighborhood {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "center_point", nullable = false, columnDefinition = "GEOMETRY(POINT, 4326)")
    private Point centerPoint;

    @Column(columnDefinition = "GEOMETRY(POLYGON, 4326)")
    private Polygon boundary;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}