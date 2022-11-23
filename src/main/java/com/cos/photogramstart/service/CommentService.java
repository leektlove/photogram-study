package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Transactional
    public Comment 댓글쓰기(String content, int imageId, int userId){
//        userRepository.findById(userId); 추천하지 않아
//        imageRepository.findById(imageId); 추천하지 않아

        //DB에서 다찾아와야하네

        //Tip 가짜 객체를 만들어서 해결 편함 ~
        //객체를 만들때 id 값만 담아서 insert 할 수 있다.
        //대신 return 시에 image 객체와 user객체는 id값만 가지고 있는 빈 객체를 받는다.
        Image image = new Image();
        image.setId(imageId);

//        User user = new User();
//        user.setId(userId);
        //user    username 못받아오네 애는 이렇게 쓰면 안되겠넹  UserRepository 필요

        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        //db에서 가져왔으니 userEntity라는 말이 맞다.

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);

    }

    @Transactional
    public void 댓글삭제(int id){
        //터지면 ~
        try{
            commentRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }
    }

    //CustomException  html 리턴
    //CustomApiException data 리턴
    //CustomValidationException 어떤 값을 처음 받을때

}
