package me.leeminsoo.usedpark.admin.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.leeminsoo.usedpark.admin.dto.AddBoardRequestDTO;
import me.leeminsoo.usedpark.admin.dto.AdminViewResponseDTO;
import me.leeminsoo.usedpark.admin.dto.UserListViewResponseDTO;
import me.leeminsoo.usedpark.config.error.exception.notpound.ItemNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.PostNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.UserNotFoundException;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.view.PostListViewResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.repository.board.BoardRepository;
import me.leeminsoo.usedpark.repository.board.CommentRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public AdminViewResponseDTO getAdminViewData () {
        return AdminViewResponseDTO.builder().boardCount(boardRepository.count()).
                postCount(postRepository.count()).
                commentCount(commentRepository.count()).
                userCount(userRepository.count()).
                itemCount(itemRepository.count()).
                build();
    }

    public void save(AddBoardRequestDTO dto) {
        try {
            boardRepository.save(new Board(dto.getName()));
        } catch (DataAccessException e){
            throw new RuntimeException("데이터베이스 접근 오류로 게시판 생성 실패");
        }
    }
    public Page<PostListViewResponseDTO> findAllPosts(String order,int page, int size ){
        Pageable pageable = setPageable(order,page,size);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> {
            int likesCount = post.getLikes().size();
            return new PostListViewResponseDTO(post, likesCount);
        });
    }
    public Page<PostListViewResponseDTO> searchPosts(String order,int page, int size,String keyword,String type){
        Pageable pageable = setPageable(order,page,size);
        Page<Post> posts;
        if("content".equals(type)){
            posts = postRepository.findByContentContaining(keyword,pageable);
        }else {
           posts = postRepository.findByTitleContaining(keyword,pageable);
        }
        return posts.map(post -> {
            int likesCount = post.getLikes().size();
            return new PostListViewResponseDTO(post, likesCount);
        });
    }
    public Page<UserListViewResponseDTO> findAllUsers(String order,int page,int size){
        Pageable pageable = setPageable(order,page,size);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> {
            int postCount = user.getPosts().size();
            int commentCount = user.getComments().size();
            return new UserListViewResponseDTO(user,postCount,commentCount);
        });
    }
    public Page<UserListViewResponseDTO> searchUsers(String order,int page,int size,String keyword,String type){
        Pageable pageable = setPageable(order,page,size);
        Page<User> users;
        if("nickname".equals(type)){
            users = userRepository.findByNicknameContaining(keyword,pageable);
        }else {
            users = userRepository.findByEmailContaining(keyword,pageable);
        }
        return users.map(user -> {
            int postCount = user.getPosts().size();
            int commentCount = user.getComments().size();
            return new UserListViewResponseDTO(user,postCount,commentCount);
        });
    }
    public Page<ItemListResponseDTO> findAllItems(String order,int page,int size) {
        Pageable pageable = setPageable(order,page,size);
        return itemRepository.findAllWithRepresentativeImage(pageable);

    }
    public Page<ItemListResponseDTO> searchItems(String order,int page,int size,String keyword,String type){
        Pageable pageable = setPageable(order,page,size);
        if ("nickname".equals(type)){
            return itemRepository.findByUserNicknameContaining(keyword,pageable);
        }else {
            return itemRepository.findByTitleContaining(keyword,pageable);
        }
    }


    public List<Board> findBoard(){
        return boardRepository.findAll();
    }
    @Transactional
    public void deletePost(Long postId, User user){
        try {
            postRepository.deleteById(postId);
            log.info("게시글 ID {} 가 관리자 {} 에 의해 삭제되었습니다.", postId, user.getNickname());
        }catch (EmptyResultDataAccessException e){
            log.error("게시글 ID {} 가 관리자 {} 에 의해 삭제 시도중 에러가 발생했습니다.", user.getNickname(), postId, e);
            throw new PostNotFoundException();
        }
    }
    @Transactional
    public void deleteUser(Long userId,User user) {
        try {
            userRepository.deleteById(userId);
            log.info("USER ID {} 가 관리자 {} 에 의해 삭제되었습니다.", userId, user.getNickname());
        }catch (EmptyResultDataAccessException e){
            log.error("USER ID {} 가 관리자 {} 에 의해 삭제 시도중 에러가 발생했습니다.", user.getNickname(), userId, e);
            throw new UserNotFoundException();
        }
    }
    @Transactional
    public void deleteItem(Long itemId,User user) {
        try{
            itemRepository.deleteById(itemId);
            log.info("Item ID {} 가 관리자 {} 에 의해 삭제되었습니다.", itemId, user.getNickname());
        }catch (EmptyResultDataAccessException e){
            log.error("Item ID {} 가 관리자 {} 에 의해 삭제 시도중 에러가 발생했습니다.", itemId, user.getNickname(), e);
            throw new ItemNotFoundException();
    }
    }


    public Pageable setPageable(String order,int page, int size) { //중복코드 제거
        Sort sort = Sort.by(Sort.Direction.fromString(order), "id");
        return PageRequest.of(page, size, sort);
    }
}
