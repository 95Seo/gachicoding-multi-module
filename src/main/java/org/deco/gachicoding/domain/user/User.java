package org.deco.gachicoding.domain.user;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

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

    private String name;
    private String email;
    private String password;
    private LocalDateTime regdate;
    private int activated;
    private String role;

    @Builder
    public User(Long idx, String name, String email, String password, LocalDateTime regdate, int activated, String role) {
        this.idx = idx;
        this.name = name;
        this.email = email;
        this.password = password;
        this.regdate = regdate;
        this.activated = activated;
        this.role = role;
    }

    public void update(String name, String email, String password, int activated, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.activated = activated;
        this.role = role;
    }
}