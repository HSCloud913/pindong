package kr.pindong.backend.service;

import kr.pindong.backend.exception.ForbiddenException;
import org.springframework.transaction.annotation.Transactional;
import kr.pindong.backend.domain.comment.Comment;
import kr.pindong.backend.domain.comment.CommentRepository;
import kr.pindong.backend.domain.pin.Pin;
import kr.pindong.backend.domain.pin.PinRepository;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.dto.request.CommentCreateRequest;
import kr.pindong.backend.dto.response.CommentResponse;
import kr.pindong.backend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PinRepository pinRepository;

    @Transactional
    public CommentResponse create(User user, UUID pinId, CommentCreateRequest request) {
        Pin pin = pinRepository.findById(pinId).orElseThrow(() -> new NotFoundException("Pin not found"));

        return CommentResponse.from(commentRepository.save(Comment.builder().user(user).pin(pin).content(request.content()).build()));
    }

    @Transactional(readOnly = true)
    public CommentResponse getOne(UUID pinId, UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getPin().getId().equals(pinId)) {
            throw new NotFoundException("Comment not found");
        }

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAll(UUID pinId) {
        pinRepository.findById(pinId).orElseThrow(() -> new NotFoundException("Pin not found"));

        return commentRepository.findByPinIdAndDeletedAtIsNullOrderByCreatedAtAsc(pinId).stream().map(CommentResponse::from).toList();
    }

    @Transactional
    public void delete(User user, UUID pinId, UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getPin().getId().equals(pinId)) {
            throw new NotFoundException("Comment not found");
        }
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("User does not own this comment");
        }

        comment.delete();
    }
}
