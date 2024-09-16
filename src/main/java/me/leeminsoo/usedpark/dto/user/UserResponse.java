package me.leeminsoo.usedpark.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String gender;
    private String phoneNumber;
    private String address;
}
