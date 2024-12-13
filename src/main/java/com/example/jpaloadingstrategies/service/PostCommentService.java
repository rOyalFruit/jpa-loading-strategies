package com.example.jpaloadingstrategies.service;

import com.example.jpaloadingstrategies.domain.Post;
import com.example.jpaloadingstrategies.domain.PostComment;
import com.example.jpaloadingstrategies.repository.PostCommentRepository;
import com.example.jpaloadingstrategies.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    public PostComment writeComment(Post post, String content) {
        PostComment postComment = PostComment.builder()
                .post(post)
                .content(content)
                .build();

        return postCommentRepository.save(postComment);
    }

    public Optional<PostComment> findById(long id) {
        return postCommentRepository.findById(id);
    }
}
