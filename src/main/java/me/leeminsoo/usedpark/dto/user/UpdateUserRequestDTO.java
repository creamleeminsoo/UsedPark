package me.leeminsoo.usedpark.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateUserRequestDTO {
    private String email;
    private String password;
    private String nickName;
    private String gender;
    private String phoneNumber;
    private String address;
}
