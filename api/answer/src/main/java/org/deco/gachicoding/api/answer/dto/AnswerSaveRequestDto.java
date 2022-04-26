package org.deco.gachicoding.api.answer.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.core.common.domain.Answer;

@Getter
@Setter
@NoArgsConstructor
public class AnswerSaveRequestDto {
    @NotNull
    private Long userIdx;

    @NotNull
    private Long queIdx;

    @NotNull
    private String ansContent;

    @Builder
    public AnswerSaveRequestDto(Long userIdx, Long queIdx, String ansContent) {
        this.userIdx = userIdx;
        this.queIdx = queIdx;
        this.ansContent = ansContent;
    }

    public Answer toEntity(){
        return Answer.builder()
                .ansContent(ansContent)
                .build();
    }
}
