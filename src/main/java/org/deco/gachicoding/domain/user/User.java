package org.deco.gachicoding.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.deco.gachicoding.domain.notice.Notice;
import org.hibernate.annotations.DynamicInsert;

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
    private Long userIdx;

    private String userName;
    private String userNick;

    private String userEmail;
    private String userPassword;
    private String userPicture;
    private LocalDateTime userRegdate;
    private boolean userActivated;
    private boolean userAuth;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Notice> notices;

    @Builder
    public User(String userName, String userNick, String userEmail, String userPassword, String userPicture, LocalDateTime userRegdate, boolean userActivated, boolean userAuth, UserRole userRole) {
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

    // 소셜 로그인 시 업데이트 되는 정보, 수정 필요
    public User socialUpdate(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        return this;
    }

    public User update(String userNick, String userPassword, boolean userActivated, boolean userAuth, UserRole userRole, String userPicture) {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.userActivated = userActivated;
        this.userAuth = userAuth;
        this.userRole = userRole;
        this.userPicture = userPicture;
        return this;
    }

//    삭제 업데이트 메서드 구현 해야함.

}