package kr.pindong.backend.controller;

import jakarta.validation.Valid;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.dto.request.CommentCreateRequest;
import kr.pindong.backend.dto.response.CommentResponse;
import kr.pindong.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pins/{pinId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@PathVariable UUID pinId, @AuthenticationPrincipal User user, @Valid @RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(201).body(commentService.create(user, pinId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getOne(@PathVariable UUID pinId, @PathVariable UUID id) {
        return ResponseEntity.ok(commentService.getOne(pinId, id));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAll(@PathVariable UUID pinId) {
        return ResponseEntity.ok(commentService.getAll(pinId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable UUID pinId, @PathVariable UUID id) {
        commentService.delete(user, pinId, id);

        return ResponseEntity.noContent().build();
    }
}
