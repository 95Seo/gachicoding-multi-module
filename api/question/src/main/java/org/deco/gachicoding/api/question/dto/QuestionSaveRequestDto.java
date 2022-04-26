package org.deco.gachicoding.api.question.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.core.common.domain.Question;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class QuestionSaveRequestDto {
    @NotNull
    private Long userIdx;

    @NotNull
    private String queTitle;

    @NotNull
    private String queContent;

    @Nullable
    private String queError;

    @Nullable
    private String queCategory;

    @Builder
    public QuestionSaveRequestDto(Long userIdx, String queTitle, String queContent, String queError, String queCategory) {
        this.userIdx = userIdx;
        this.queTitle = queTitle;
        this.queContent = queContent;
        this.queError = queError;
        this.queCategory = queCategory;
    }

    public Question toEntity(){
        return Question.builder()
                .queTitle(queTitle)
                .queContent(queContent)
                .queError(queError)
                .queCategory(queCategory)
                .build();
    }
}
