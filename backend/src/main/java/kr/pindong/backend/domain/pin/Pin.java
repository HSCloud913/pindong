package kr.pindong.backend.domain.pin;

import jakarta.persistence.*;
import kr.pindong.backend.domain.neighborhood.Neighborhood;
import kr.pindong.backend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pins")
@Getter
@NoArgsConstructor
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighborhood_id")
    private Neighborhood neighborhood;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "GEOMETRY(POINT, 4326)")
    private Point location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PinStatus status;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Pin(User user, Neighborhood neighborhood, String category,
               String title, String content, Point location) {
        this.user = user;
        this.neighborhood = neighborhood;
        this.category = category;
        this.title = title;
        this.content = content;
        this.location = location;
        this.status = PinStatus.active;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusDays(7);
    }

    public void update(String category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;

        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = PinStatus.deleted;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
