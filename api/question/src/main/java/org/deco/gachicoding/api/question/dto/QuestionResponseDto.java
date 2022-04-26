package org.deco.gachicoding.api.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.api.answer.dto.AnswerResponseDto;
import org.deco.gachicoding.core.common.domain.Answer;
import org.deco.gachicoding.core.common.domain.Question;
import org.deco.gachicoding.core.common.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponseDto {

    private Long queIdx;
    private Long userIdx;
    private List<AnswerResponseDto> answerList = new ArrayList<>();
    private String queTitle;
    private String queContent;
    private String queError;
    private String queCategory;
    private Boolean queSolve;
    private Boolean queActivated;
    private LocalDateTime queRegdate;

    @Builder
    public QuestionResponseDto(Question question) {
        setWriterInfo(question);
        setAnswerList(question);
        this.queIdx = question.getQueIdx();
        this.queTitle = question.getQueTitle();
        this.queContent = question.getQueContent();
        this.queError = question.getQueError();
        this.queCategory = question.getQueCategory();
        this.queSolve = question.getQueSolve();
        this.queActivated = question.getQueActivated();
        this.queRegdate = question.getQueRegdate();
    }

    private void setWriterInfo(Question question) {
        User user = question.getUser();
        this.userIdx = user.getUserIdx();
    }

    private void setAnswerList(Question question) {
        for(Answer ans : question.getAnswers()) {
            AnswerResponseDto answerResponseDto = AnswerResponseDto.builder()
                    .answer(ans).build();
            answerList.add(answerResponseDto);
        }
    }
}
