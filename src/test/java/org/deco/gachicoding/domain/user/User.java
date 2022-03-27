package org.deco.gachicoding.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long userIdx;

    private String userName;
    private String userNick;
    private String userEmail;
    private String userPassword;
    private LocalDateTime userRegdate;
    private int userActivated;
    private boolean userAuth;
    private String userPicture;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    public User(String userName, String userNick, String userEmail, String userPassword, LocalDateTime userRegdate, int userActivated, boolean userAuth, String userPicture, UserRole userRole) {
        this.userName = userName;
        this.userNick = userNick;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRegdate = userRegdate;
        this.userActivated = userActivated;
        this.userAuth = userAuth;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }

    // 소셜 로그인 시 업데이트 되는 정보, 수정 필요
    public User socialUpdate(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        return this;
    }

    // 유저 정보 업데이트 이메일 수정이 가능한게 맞을까?
    public User update(String userNick, String userPassword, int userActivated, boolean userAuth, String userPicture, UserRole userRole) {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.userActivated = userActivated;
        this.userAuth = userAuth;
        this.userPicture = userPicture;
        this.userRole = userRole;
        return this;
    }
}