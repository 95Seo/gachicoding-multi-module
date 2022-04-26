package org.deco.gachicoding.api.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {

    private String queTitle;
    private String queContent;
    private String queError;
    private String queCategory;

    public QuestionUpdateRequestDto(String queTitle, String queContent, String queError, String queCategory) {
        this.queTitle = queTitle;
        this.queContent = queContent;
        this.queError = queError;
        this.queCategory = queCategory;
    }
}
