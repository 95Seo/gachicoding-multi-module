package org.deco.gachicoding.domain.user;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Nullable
    private String name;
    @Nullable
    private String email;
    private String password;
    private LocalDateTime regdate;
    private int activated;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long idx, String name, String email, String password, LocalDateTime regdate, int activated, Role role) {
        this.idx = idx;
        this.name = name;
        this.email = email;
        this.password = password;
        this.regdate = regdate;
        this.activated = activated;
        this.role = role;
    }

    public User socialUpdate(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public User update(String name, String email, String password, int activated, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.activated = activated;
        this.role = role;
        return this;
    }
}