package me.leeminsoo.usedpark.admin.dto;

import lombok.Getter;
import lombok.Setter;
import me.leeminsoo.usedpark.domain.user.Role;
import me.leeminsoo.usedpark.domain.user.User;

@Getter
@Setter
public class UserListViewResponseDTO {
    private Long id;
    private String email;
    private String nickName;
    private String gender;
    private String phoneNumber;
    private String address;
    private Role roles;
    private int postCount;
    private int commentCount;

    public UserListViewResponseDTO(User user, int postCount, int commentCount){
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.roles = user.getRoles();
        this.postCount = postCount;
        this.commentCount = commentCount;
    }
}
