package org.deco.gachicoding.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.core.common.domain.User;
import org.deco.gachicoding.core.common.domain.UserRole;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long userIdx;
    private String userName;
    private String userNick;
    private String userEmail;
    private String userPassword;
    private String userPicture;
    private LocalDateTime userRegdate;
    private boolean userActivated;
    private boolean userAuth;
    private UserRole userRole;

    public UserResponseDto(User user) {
        this.userIdx = user.getUserIdx();
        this.userName = user.getUserName();
        this.userNick = user.getUserNick();
        this.userEmail = user.getUserEmail();
        this.userPassword = user.getUserPassword();
        this.userPicture = user.getUserPicture();
        this.userRegdate = user.getUserRegdate();
        this.userActivated = user.getUserActivated();
        this.userAuth = user.getUserAuth();
        this.userRole = user.getUserRole();
    }

}
