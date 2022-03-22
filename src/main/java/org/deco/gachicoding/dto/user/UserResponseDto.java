package org.deco.gachicoding.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.Role;
import org.deco.gachicoding.domain.user.User;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long idx;
    private String name;
    private String email;
    private String password;
    private LocalDateTime regdate;
    private int activated;
    private Role role;

    public UserResponseDto(User user) {
        this.idx = user.getIdx();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.regdate = user.getRegdate();
        this.activated = user.getActivated();
        this.role = user.getRole();
    }

}
