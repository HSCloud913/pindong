package kr.pindong.backend.service;

import kr.pindong.backend.domain.pin.Pin;
import kr.pindong.backend.domain.pin.PinRepository;
import kr.pindong.backend.domain.pin.PinStatus;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.dto.request.PinCreateRequest;
import kr.pindong.backend.dto.request.PinUpdateRequest;
import kr.pindong.backend.dto.response.PinResponse;
import kr.pindong.backend.exception.ForbiddenException;
import kr.pindong.backend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PinService {
    private final PinRepository pinRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public PinResponse create(User user, PinCreateRequest request) {
        Point location = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

        return PinResponse.from(pinRepository.save(Pin.builder().user(user).category(request.category()).title(request.title()).content(request.content()).location(location).build()));
    }

    @Transactional
    public PinResponse getOne(UUID id) {
        Pin pin = pinRepository.findById(id).orElseThrow(() -> new NotFoundException("Pin not found"));
        pin.incrementViewCount();

        return PinResponse.from(pin);
    }

    @Transactional(readOnly = true)
    public List<PinResponse> getMe(User user) {
        return pinRepository.findByUserAndStatusNotOrderByCreatedAtDesc(user, PinStatus.deleted).stream().map(PinResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<PinResponse> getNearBy(double latitude, double longitude, double radius) {
        return pinRepository.findNearby(latitude, longitude, radius).stream().map(PinResponse::from).toList();
    }

    @Transactional
    public PinResponse update(User user, UUID pinId, PinUpdateRequest request) {
        Pin pin = pinRepository.findById(pinId).orElseThrow(() -> new NotFoundException("Pin not found"));
        if (!pin.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("User does not own this pin");
        }

        pin.update(request.category(), request.title(), request.content());

        return PinResponse.from(pin);
    }

    @Transactional
    public void delete(User user, UUID pinId) {
        Pin pin = pinRepository.findById(pinId).orElseThrow(() -> new NotFoundException("Pin not found"));
        if (!pin.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("User does not own this pin");
        }

        pin.delete();
    }
}
