package org.deco.gachicoding.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.Role;
import org.deco.gachicoding.domain.user.User;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String userName;
    private String userNick;
    @Nullable
    @Email(message = "올바른 형식의 아이디가 아닙니다.")
    private String userEmail;
    private String userPassword;
    private String userPicture;
    private Role userRole;

    @Builder
    public UserSaveRequestDto(String userName, String userEmail, String userPassword, String userNick, String userPicture, Role userRole) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNick = userNick;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .userNick(userNick)
                .userPicture(userPicture)
                .userRole(userRole)
                .build();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        userPassword = passwordEncoder.encode(userPassword);
    }
}
