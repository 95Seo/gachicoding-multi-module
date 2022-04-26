package org.deco.gachicoding.api.answer.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.answer.dto.AnswerResponseDto;
import org.deco.gachicoding.api.answer.dto.AnswerSaveRequestDto;
import org.deco.gachicoding.api.answer.dto.AnswerUpdateRequestDto;
import org.deco.gachicoding.api.answer.service.AnswerService;
import org.deco.gachicoding.core.common.domain.Answer;
import org.deco.gachicoding.core.common.repository.AnswerRepository;
import org.deco.gachicoding.core.common.repository.QuestionRepository;
import org.deco.gachicoding.core.common.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public AnswerResponseDto getAnswerDetailById(Long answerIdx) {
        Optional<Answer> answer = answerRepository.findById(answerIdx);
        AnswerResponseDto answerDetail = AnswerResponseDto.builder()
                .answer(answer.get())
                .build();
        return answerDetail;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnswerResponseDto> getAnswerListByKeyword(String keyword, int page) {
        Page<Answer> answers = answerRepository.findByAnsContentContainingIgnoreCaseAndAnsActivatedTrueOrderByAnsIdxDesc(keyword, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "asIdx")));
        Page<AnswerResponseDto> answersList = answers.map(
                result -> new AnswerResponseDto(result)
        );
        return answersList;
    }

    @Override
    @Transactional
    public Long registerAnswer(AnswerSaveRequestDto dto) {
        Answer answer = dto.toEntity();

        // findById() -> 실제로 데이터베이스에 도달하고 실제 오브젝트 맵핑을 데이터베이스의 행에 리턴한다. 데이터베이스에 레코드가없는 경우 널을 리턴하는 것은 EAGER로드 한것이다.
        // getOne ()은 내부적으로 EntityManager.getReference () 메소드를 호출한다. 데이터베이스에 충돌하지 않는 Lazy 조작이다. 요청된 엔티티가 db에 없으면 EntityNotFoundException을 발생시킨다.
        answer.setUser(userRepository.getOne(dto.getUserIdx()));

        answer.setQuestion(questionRepository.getOne(dto.getQueIdx()));

        return answerRepository.save(answer).getAnsIdx();
    }

    @Override
    @Transactional
    public AnswerResponseDto modifyAnswerById(Long answerIdx, AnswerUpdateRequestDto dto) {

        Answer answer = answerRepository.findById(answerIdx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + answerIdx));

        answer = answer.update(dto.getAnsContent());

        AnswerResponseDto answerDetail = AnswerResponseDto.builder()
                .answer(answer)
                .build();

        return answerDetail;
    }

    @Override
    @Transactional
    public Long removeAnswer(Long answerIdx) {

        Answer answer = answerRepository.findById(answerIdx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + answerIdx));

        return answer.isDisable().getAnsIdx();
    }
}
