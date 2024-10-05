package me.leeminsoo.usedpark.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.EmailDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.UserNotFoundException;
import me.leeminsoo.usedpark.domain.user.Role;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.user.AddUserRequestDTO;
import me.leeminsoo.usedpark.dto.user.UpdateUserRequestDTO;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public void save(AddUserRequestDTO dto) {
        String email = dto.getEmail();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (userRepository.existsByEmail(email)){
            throw new EmailDuplicationException();
        }
         userRepository.save(User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickName())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                 .roles(Role.USER)
                .build());
    }

    public User findByUser(String email){
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void update(UpdateUserRequestDTO dto, Long userid){
        User user = userRepository.findById(userid).orElseThrow(UserNotFoundException::new);
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new EmailDuplicationException();
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.update(dto.getEmail(),bCryptPasswordEncoder.encode(dto.getPassword()),
                dto.getNickName(), dto.getGender(), dto.getPhoneNumber(), dto.getAddress());

    }
    @Transactional
    public void deleteUser(Long userId,User user) {
        try {
            userRepository.deleteById(userId);
            logger.info("User with ID {} was deleted by user Email {}", userId, user.getEmail());
        }catch (EmptyResultDataAccessException e){
            logger.error("Error occurred while user Email {} tried to delete user with ID {}", user.getEmail(), userId, e);
            throw new UserNotFoundException();
        }
    }

}
