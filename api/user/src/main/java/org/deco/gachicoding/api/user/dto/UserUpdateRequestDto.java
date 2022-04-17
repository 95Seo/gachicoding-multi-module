package org.deco.gachicoding.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.core.common.domain.UserRole;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String userPassword;
    private String userNick;
    private String userPicture;
    private UserRole userRole;
//    private boolean userActivated;
//    private boolean userAuth;

    @Builder
    public UserUpdateRequestDto(String userNick, String userPassword, UserRole userRole, String userPicture) {
        this.userNick = userNick;
        this.userPassword = userPassword;
//        this.userActivated = userActivated;
//        this.userAuth = userAuth;
        this.userRole = userRole;
        this.userPicture = userPicture;
    }
}
