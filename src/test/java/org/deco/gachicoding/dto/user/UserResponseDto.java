package org.deco.gachicoding.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRole;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String userName;
    private String userNick;
    private String userEmail;
    private String userPassword;
    private LocalDateTime userRegdate;
    private int userActivated;
    private boolean userAuth;
    private String userPicture;
    private UserRole userRole;

    public UserResponseDto(User user) {
        this.userName = user.getUserName();
        this.userNick = user.getUserNick();
        this.userEmail = user.getUserEmail();
        this.userPassword = user.getUserPassword();
        this.userRegdate = user.getUserRegdate();
        this.userActivated = user.getUserActivated();
        this.userAuth = user.isUserAuth();
        this.userPicture = user.getUserPicture();
        this.userRole = user.getUserRole();
    }
}