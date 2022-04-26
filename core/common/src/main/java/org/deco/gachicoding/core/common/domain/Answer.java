package org.deco.gachicoding.core.common.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "gachi_a")
public class Answer {
    @Id
    @Column(name = "ans_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ansIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "que_idx")
    @JsonManagedReference
    private Question question;

    @JoinColumn(name = "ans_content")
    private String ansContent;

    @JoinColumn(name = "ans_select")
    private Boolean ansSelect;

    @JoinColumn(name = "ans_activated")
    private Boolean ansActivated;

    @JoinColumn(name = "ans_regdate")
    private LocalDateTime ansRegdate;

    @Builder
    public Answer(User user, Question question, String ansContent, Boolean ansSelect, Boolean ansActivated, LocalDateTime ansRegdate) {
        this.user = user;
        this.question = question;
        this.ansContent = ansContent;
        this.ansSelect = ansSelect;
        this.ansActivated = ansActivated;
        this.ansRegdate = ansRegdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer update(String ansContent) {
        this.ansContent = ansContent;
        return this;
    }

    public Answer isDisable() {
        this.ansActivated = false;
        return this;
    }

    public Answer isEnable() {
        this.ansActivated = true;
        return this;
    }
}