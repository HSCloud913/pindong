package kr.pindong.backend.controller;

import jakarta.validation.Valid;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.dto.request.CommentCreateRequest;
import kr.pindong.backend.dto.response.CommentResponse;
import kr.pindong.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<CommentResponse>> getAll(@PageableDefault(size = 20) Pageable pageable, @PathVariable UUID pinId) {
        return ResponseEntity.ok(commentService.getAll(pageable, pinId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable UUID pinId, @PathVariable UUID id) {
        commentService.delete(user, pinId, id);

        return ResponseEntity.noContent().build();
    }
}
