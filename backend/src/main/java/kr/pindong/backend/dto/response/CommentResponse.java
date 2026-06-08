package kr.pindong.backend.dto.response;

import kr.pindong.backend.domain.comment.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(UUID id,
                              UUID pinId,
                              String authorNickname,
                              String content,
                              LocalDateTime createdAt) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPin().getId(),
                comment.getUser().getNickname(),
                comment.isDeleted() ? "삭제된 댓글입니다" : comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
