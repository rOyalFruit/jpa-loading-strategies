package com.example.jpaloadingstrategies.global;

import com.example.jpaloadingstrategies.domain.Post;
import com.example.jpaloadingstrategies.domain.PostComment;
import com.example.jpaloadingstrategies.repository.PostCommentRepository;
import com.example.jpaloadingstrategies.service.PostCommentService;
import com.example.jpaloadingstrategies.service.PostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;
    private final PostCommentService postCommentService;

    /**
     * 초기 데이터 생성
     */
    @Order(1)
    @Bean
    public ApplicationRunner InitDataRunner(){
        return args -> {
            if(postService.count() > 0) return;

            Post post1 = postService.write("title1", "content1");
            Post post2 = postService.write("title2", "content2");
            Post post3 = postService.write("title3", "content3");

            // 1번 글에 대해서 댓글 2개 작성
            PostComment postComment1 = postCommentService.writeComment(post1, "comment1");
            PostComment postComment2 = postCommentService.writeComment(post1, "comment2");

            // 2번 글에 대해서 댓글 1개 작성
            PostComment postComment3 = postCommentService.writeComment(post2, "comment3");
        };
    }

//    /**
//     * 즉시 로딩 예시.
//     * PostComment 엔티티의 fetch 타입을 EAGER로 변경 필요
//     */
//    @Bean
//    @Order(2)
//    public ApplicationRunner eagerLoadingApplicationRunner() {
//        return args -> {
//            PostComment postComment3 = postCommentService.findById(3).get();
//
//            Post postOfComment3 = postComment3.getPost();
//
//            System.out.println("즉시 로딩: " + postOfComment3.toString());
//        };
//    }

    /**
     * 지연 로딩(트랜잭션 O) 예시.
     * PostComment.class의 post 필드에 @ManyToOne(fetch = FetchType.LAZY) 적용해야 함.
     */
    @Bean
    @Order(3)
    public ApplicationRunner LazyLoadingWithTransactionRunner() {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                PostComment postComment3 = postCommentService.findById(3).get();

                Post postOfComment3 = postComment3.getPost();

                System.out.println("postOfComment3.getId() = " + postOfComment3.getId());
                System.out.println("postOfComment3.getTitle() = " + postOfComment3.getTitle());
            }
        };
    }

    /**
     * 지연 로딩 (트랜잭션 X) 예시.
     * PostComment.class의 post 필드에 @ManyToOne(fetch = FetchType.LAZY) 적용해야 함.
     */
    @Bean
    @Order(4)
    public ApplicationRunner LazyLoadingWithoutTransactionRunner() {
        return args -> {
            PostComment postComment3 = postCommentService.findById(3).get(); // 값 가져오고 DB 커넥션 닫힘

            Post postOfComment3 = postComment3.getPost(); // 값 가져오고 DB 커넥션 닫힘
            System.out.println("postOfComment3.getId() = " + postOfComment3.getId()); // postOfComment3에 id값은 들어 있으므로 정상적으로 동작.
            try{
                System.out.println("postOfComment3.getTitle() = " + postOfComment3.getTitle()); // 값이 없어서 가져와야 하지만 세션이 없어서 값을 가져오지 못하고 에러남.
            }catch (LazyInitializationException e){
                System.out.println("LazyInitializationException 발생");
            }
        };
    }
}




