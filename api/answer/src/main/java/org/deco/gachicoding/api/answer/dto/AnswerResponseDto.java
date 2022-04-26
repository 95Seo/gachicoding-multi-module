package org.deco.gachicoding.api.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.core.common.domain.Answer;
import org.deco.gachicoding.core.common.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AnswerResponseDto {

    private Long ansIdx;
    private Long userIdx;
    private String ansContent;
    private Boolean ansSelect;
    private Boolean ansActivated;
    private LocalDateTime ansRegdate;

    @Builder
    public AnswerResponseDto(Answer answer) {
        setWriterInfo(answer);
        this.ansIdx = answer.getAnsIdx();
        this.ansContent = answer.getAnsContent();
        this.ansSelect = answer.getAnsSelect();
        this.ansActivated = answer.getAnsActivated();
        this.ansRegdate = answer.getAnsRegdate();
    }

    private void setWriterInfo(Answer answer) {
        User user = answer.getUser();
        this.userIdx = user.getUserIdx();
    }


}
