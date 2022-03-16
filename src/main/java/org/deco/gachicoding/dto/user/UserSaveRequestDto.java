package org.deco.gachicoding.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.User;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String name;
    private String email;
    private String password;

    @Builder
    public UserSaveRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
