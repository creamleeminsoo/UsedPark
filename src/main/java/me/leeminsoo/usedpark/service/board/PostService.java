package me.leeminsoo.usedpark.service.board;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.BoardNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.PostNotFoundException;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.board.PostImage;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.AddPostRequestDTO;
import me.leeminsoo.usedpark.dto.board.post.UpdatePostDTO;
import me.leeminsoo.usedpark.dto.board.post.view.PopularPostsResponseDTO;
import me.leeminsoo.usedpark.dto.board.post.view.PostListViewResponseDTO;
import me.leeminsoo.usedpark.dto.board.post.view.PostResponseDTO;
import me.leeminsoo.usedpark.repository.board.BoardRepository;
import me.leeminsoo.usedpark.repository.board.ImageRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import me.leeminsoo.usedpark.service.file.S3Service;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;
    private final S3Service s3Service;

    @Autowired
    private EntityManager entityManager;


    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
    public PostResponseDTO getPost(Long postId) {

        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.clear();
        System.out.println("******게시글 하나 조회 (댓글 5개, 좋아요 3개)******");
        Post post = postRepository.findByIdWithFetchJoin(postId)
                .orElseThrow(PostNotFoundException::new);

        PostResponseDTO responseDTO = PostResponseDTO.builder().id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .comments(post.getComments())
                .nickName(post.getUser().getNickname())
                .images(post.getImages())
                .view(post.getView())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikes().size())
                .build();
        long queryCount = statistics.getPrepareStatementCount();
        System.out.println("게시글 하나 조회할시 쿼리 (댓글 5개, 좋아요 3개) = " + queryCount);

        return responseDTO;
    }

    @Transactional
    public void delete(Long postId,User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<PostImage> images = imageRepository.findByPostId(postId);
        if(post.getUser().getId().equals(user.getId())) {
            this.deleteImage(images, postId);
            postRepository.deleteById(postId);
        }else throw new AccessDeniedException("권한이 없습니다");

    }

    public Post save(AddPostRequestDTO dto, List<MultipartFile> imageFiles) {


        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .board(dto.getBoard())
                .user(dto.getUser())
                .build();

        postRepository.save(post);


        if (!(imageFiles == null)) {
            imageSave(imageFiles, post);
        }
        return post;
    }
    @Transactional
    public Post update(Long postId, UpdatePostDTO dto, User user,List<MultipartFile> imageFiles) {

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<PostImage> images = imageRepository.findByPostId(postId);
        if (post.getUser().getId().equals(user.getId())) {
            this.deleteImage(images,postId);
            post.update(dto.getTitle(), dto.getContent());
        } else throw new AccessDeniedException("권한이 없습니다");

        if (imageFiles != null) {
            imageSave(imageFiles, post);
        }
        return post;
    }

    public Page<PostListViewResponseDTO> getPosts(Long boardId, String order, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = postRepository.findByBoardId(pageable,boardId);
        return posts.map(post -> {
            int likesCount = post.getLikes().size();
            return new PostListViewResponseDTO(post, likesCount);
        });
    }

    public void imageSave(List<MultipartFile> images, Post post) {
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                if (!isValidImageFile(file)) {
                    throw new IllegalArgumentException("PNG 또는 JPEG 파일만 업로드 가능합니다");
                }

                String imageUrl = s3Service.upload(file);

                PostImage postImage = PostImage.builder()
                        .url(imageUrl)
                        .post(post)
                        .build();

                imageRepository.save(postImage);
            }
        }
    }
    public boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE);
    }



    @Transactional
    public Page<PostListViewResponseDTO> searchPost(Long boardId,String keyword,String type,String order,int page,int size) {

        Sort sort = Sort.by(Sort.Direction.fromString(order), "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        if(type.equals("title")) {
            return postRepository.findByTitleContainingAndBoardId(keyword,boardId, pageable).map(PostListViewResponseDTO::new);
        }else  {
            return postRepository.findByContentContainingAndBoardId(keyword,boardId, pageable).map(PostListViewResponseDTO::new);
        }
    }

    public void deleteImage(List<PostImage> images, Long postId) {
        for (PostImage image : images) {
            String imagePath = image.getUrl();
            String fileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);

            imageRepository.deleteByPostId(postId);
            s3Service.delete(fileName);
        }
    }

    @Transactional
    public void updateView(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }else
            postRepository.updateView(postId);
    }


    public Page<PostListViewResponseDTO> findByBoardIdOrderByLikes(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByBoardIdOrderByLikes(pageable, boardId);

        return posts.map(post -> {
            int likesCount = post.getLikes().size();
            return new PostListViewResponseDTO(post, likesCount);
        });
    }
    public String findByBoardName(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return board.getName();
    }

    public Page<PostListViewResponseDTO> findByUserId(Long userId,Long boardId,int page,int size,String order){
        Sort sort = Sort.by(Sort.Direction.fromString(order), "id");
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Post> posts;
        if (boardId == 0){
            posts = postRepository.findByUserId(pageable,userId);
        }else {
            posts = postRepository.findByUserIdAndBoardId(pageable,userId,boardId);
        }
        return posts.map(post -> {
            int likesCount = post.getLikes().size();
            return new PostListViewResponseDTO(post, likesCount);
        });

    }
    public List<PopularPostsResponseDTO> getPopularPosts(){
        Pageable topTen = PageRequest.of(0, 5); // JPQL에선 LIMIT키워드를 사용할수없으므로 Pageable인터페이스로 5개의 글만 가져오도록 설정
        List<Post> posts = postRepository.findTopPostsOrderByLikes(topTen);

        return posts.stream().map(post -> {
            int commentCount = post.getComments().size();
            int likeCount = post.getLikes().size();
            return new PopularPostsResponseDTO(post,commentCount,likeCount);
        }).toList();
    }
}
