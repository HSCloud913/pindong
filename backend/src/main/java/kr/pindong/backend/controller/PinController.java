package kr.pindong.backend.controller;

import jakarta.validation.Valid;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.dto.request.PinCreateRequest;
import kr.pindong.backend.dto.request.PinUpdateRequest;
import kr.pindong.backend.dto.response.PinResponse;
import kr.pindong.backend.service.PinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pins")
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;

    @PostMapping
    public ResponseEntity<PinResponse> create(@AuthenticationPrincipal User user, @Valid @RequestBody PinCreateRequest request) {
        return ResponseEntity.status(201).body(pinService.create(user, request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<PinResponse> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(pinService.getOne(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PinResponse>> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(pinService.getMe(user));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<PinResponse>> getNearBy(@RequestParam double latitude, @RequestParam double longitude, @RequestParam(defaultValue = "1000") double radius) {
        return ResponseEntity.ok(pinService.getNearBy(latitude, longitude, radius));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PinResponse> update(@AuthenticationPrincipal User user, @PathVariable UUID id, @Valid @RequestBody PinUpdateRequest request) {
        return ResponseEntity.ok(pinService.update(user, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        pinService.delete(user, id);
        return ResponseEntity.noContent().build();
    }
}
