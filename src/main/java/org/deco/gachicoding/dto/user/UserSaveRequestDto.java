package org.deco.gachicoding.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.user.User;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String name;
    @Nullable
    @Email(message = "올바른 형식의 아이디가 아닙니다.")
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

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
