package me.leeminsoo.usedpark.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.EmailDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.UserNotFoundException;
import me.leeminsoo.usedpark.domain.user.Role;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.user.AddUserRequestDTO;
import me.leeminsoo.usedpark.dto.user.UpdateUserRequestDTO;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;


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

    public User findUser(String email){
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
    public List<User> findAllById(List<Long> usersId){
        return userRepository.findAllById(usersId);
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
            log.info("User ID {} 님이 회원 탈퇴를 하였습니다",user.getId());
        }catch (EmptyResultDataAccessException e){
            log.info("User ID {} 님이 회원 탈퇴를 시도중 예외가 발생했습니다.",user.getId());
            throw new UserNotFoundException();
        }
    }

}
