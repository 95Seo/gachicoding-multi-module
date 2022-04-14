package org.deco.gachicoding.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.UserRole;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String userPassword;
    private String userNick;
    private String userPicture;
    private UserRole userRole;
    private boolean userActivated;
    private boolean userAuth;

    public UserUpdateRequestDto(String userNick, String userPassword, boolean userActivated, boolean userAuth, UserRole userRole, String userPicture) {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.userActivated = userActivated;
        this.userAuth = userAuth;
        this.userRole = userRole;
        this.userPicture = userPicture;
    }
}
