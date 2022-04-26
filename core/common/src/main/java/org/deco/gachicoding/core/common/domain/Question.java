package org.deco.gachicoding.core.common.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "gachi_q")
public class Question {
    @Id
    @Column(name = "que_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @JsonManagedReference
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @JsonBackReference
    private List<Answer> answers = new ArrayList<>();

    @Column(name = "que_title")
    private String queTitle;

    @Column(name = "que_content")
    private String queContent;

    @Column(name = "que_error")
    private String queError;

    @Column(name = "que_category")
    private String queCategory;

    @Column(name = "que_solve")
    private Boolean queSolve;

    @Column(name = "que_activated")
    private Boolean queActivated;

    @Column(name = "que_regdate")
    private LocalDateTime queRegdate;

    @Builder
    public Question(User user, Long queIdx, String queTitle, String queContent, String queError, String queCategory, Boolean queSolve, Boolean queActivated, LocalDateTime queRegdate) {
        this.user = user;
        this.queIdx = queIdx;
        this.queTitle = queTitle;
        this.queContent = queContent;
        this.queError = queError;
        this.queCategory = queCategory;
        this.queSolve = queSolve;
        this.queActivated = queActivated;
        this.queRegdate = queRegdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAnswers(Answer answer) {
        this.answers.add(answer);
    }

    public Question update(String queTitle, String queContent, String queError, String queCategory) {
        this.queTitle = queTitle;
        this.queContent = queContent;
        this.queError = queError;
        this.queCategory = queCategory;
        return this;
    }

    public Question isDisable() {
        this.queActivated = false;
        return this;
    }

    public Question isEnable() {
        this.queActivated = true;
        return this;
    }

}