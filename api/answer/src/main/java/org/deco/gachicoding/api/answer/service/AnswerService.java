package org.deco.gachicoding.api.answer.service;

import org.deco.gachicoding.api.answer.dto.AnswerResponseDto;
import org.deco.gachicoding.api.answer.dto.AnswerSaveRequestDto;
import org.deco.gachicoding.api.answer.dto.AnswerUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {
    AnswerResponseDto getAnswerDetailById(Long answerIdx);

    Page<AnswerResponseDto> getAnswerListByKeyword(String keyword, int page);

    Long registerAnswer(AnswerSaveRequestDto dto);

    AnswerResponseDto modifyAnswerById(Long answerIdx, AnswerUpdateRequestDto dto);

    Long removeAnswer(Long answerIdx);
}
