package me.leeminsoo.usedpark.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequestDTO {
    private String email;
    private String password;
    private String nickName;
    private String gender;
    private String phoneNumber;
    private String address;
}
