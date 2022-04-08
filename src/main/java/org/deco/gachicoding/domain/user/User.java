package org.deco.gachicoding.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.deco.gachicoding.domain.notice.Notice;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@DynamicInsert
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Nullable
    private String userName;
    private String userNick;

    @Nullable
    private String userEmail;
    private String userPassword;
    private String userPicture;
    private LocalDateTime userRegdate;
    private boolean userActivated;
    private boolean userAuth;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Notice> notices;

    @Builder
    public User(Long userIdx, String userName, String userNick, String userEmail, String userPassword, String userPicture, LocalDateTime userRegdate, boolean userActivated, boolean userAuth, Role userRole) {
        this.userIdx = userIdx;
        this.userName = userName;
        this.userNick = userNick;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRegdate = userRegdate;
        this.userPicture = userPicture;
        this.userActivated = userActivated;
        this.userAuth = userAuth;   // 인증여부 칼럼
        this.userRole = userRole;
    }

    public User socialUpdate(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        return this;
    }

    public User update(String userPassword, String userNick, String userPicture, Role userRole) {
        this.userPassword = userPassword;
        this.userNick = userNick;
        this.userPicture = userPicture;
        this.userRole = userRole;
        return this;
    }
}