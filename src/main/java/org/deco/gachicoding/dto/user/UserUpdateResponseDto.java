package org.deco.gachicoding.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.Role;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {

    private String userPassword;
    private String userNick;
    private String userPicture;
    private Role userRole;

    public UserUpdateResponseDto(String userPassword, String userNick, String userPicture, boolean userActivated, Role userRole) {
        this.userPassword = userPassword;
        this.userNick = userNick;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }
}
