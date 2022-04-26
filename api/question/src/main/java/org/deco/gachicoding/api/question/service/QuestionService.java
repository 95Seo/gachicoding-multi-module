package org.deco.gachicoding.api.question.service;

import org.deco.gachicoding.api.question.dto.QuestionResponseDto;
import org.deco.gachicoding.api.question.dto.QuestionSaveRequestDto;
import org.deco.gachicoding.api.question.dto.QuestionUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    QuestionResponseDto getQuestionDetailById(Long questionIdx);

    Page<QuestionResponseDto> getQuestionListByKeyword(String keyword, int page);

    Long registerQuestion(QuestionSaveRequestDto dto);

    QuestionResponseDto modifyQuestionById(Long questionIdx, QuestionUpdateRequestDto dto);

    Long removeQuestion(Long questionIdx);
}
