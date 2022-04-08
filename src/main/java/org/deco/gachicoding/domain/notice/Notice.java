package org.deco.gachicoding.domain.notice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@Entity(name = "notice")
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notIdx;

    private String notTitle;
    private String notContent;
    private int notViews;
    private Boolean notPin;
    private Boolean notActivate;
    private LocalDateTime notRegdate;

    // FetchType.EAGER 즉시 로딩
    // 1. 대부분의 JPA 구현체는 가능하면 조인을 사용해서 SQL 한번에 함께 조회하려고 한다.
    // 2. 이렇게 하면, 실제 조회할 때 한방 쿼리로 다 조회해온다.
    // FetchType.LAZY 지연 로딩
    // 1. 로딩되는 시점에 Lazy 로딩 설정이 되어있는 Team 엔티티는 프록시 객체로 가져온다.
    // 2. 후에 실제 객체를 사용하는 시점에 초기화가 된다. DB에 쿼리가 나간다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @JsonManagedReference
    private User user;

    @Builder
    public Notice(User user, String notTitle, String notContent, int notViews, Boolean notPin, Boolean notActivate, LocalDateTime notRegdate) {
        this.user = user;
        this.notTitle = notTitle;
        this.notContent = notContent;
        this.notViews = notViews;
        this.notPin = notPin;
        this.notActivate = notActivate;
        this.notRegdate = notRegdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notice update(String notTitle, String notContent, Boolean notPin) {
        if(notTitle != null)
            this.notTitle = notTitle;
        if(notContent != null)
            this.notContent = notContent;
        if(notPin != null)
            this.notPin = notPin;
        return this;
    }

    public Notice delete(Boolean notActivate) {
        this.notActivate = notActivate;
        return this;
    }
}
