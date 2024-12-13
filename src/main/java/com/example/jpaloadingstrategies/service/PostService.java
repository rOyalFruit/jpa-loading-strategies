package com.example.jpaloadingstrategies.service;

import com.example.jpaloadingstrategies.domain.Post;
import com.example.jpaloadingstrategies.domain.PostComment;
import com.example.jpaloadingstrategies.repository.PostCommentRepository;
import com.example.jpaloadingstrategies.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post write(String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();
        postRepository.save(post);

        return post;
    }

    public long count() {
        return postRepository.count();
    }
}
