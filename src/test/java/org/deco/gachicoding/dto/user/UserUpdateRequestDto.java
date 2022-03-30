package org.deco.gachicoding.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String userNick;
    private String userPassword;
    private String userPicture;

    public UserUpdateRequestDto(String userNick, String userPassword, String userPicture) {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.userPicture = userPicture;
    }
}
