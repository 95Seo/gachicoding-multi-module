package org.deco.gachicoding.core.common.repository;

import org.deco.gachicoding.core.common.domain.Answer;
import org.deco.gachicoding.core.common.domain.Question;
import org.deco.gachicoding.core.common.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    private Long createAnswerMock(String ansContent) {
        Long userIdx = Long.valueOf(1);
        Long queIdx = Long.valueOf(1);

        User user = User.builder()
                .userIdx(userIdx)
                .build();

        Question question = Question.builder()
                .queIdx(queIdx)
                .build();

        Answer entity = Answer.builder()
                .user(user)
                .question(question)
                .ansContent(ansContent)
                .build();

        return answerRepository.save(entity).getAnsIdx();
    }

    @Test
    public void 인덱스로_답변_조회() {
        String ansContent = "답변 테스트 내용";
        Long noticeIdx = createAnswerMock(ansContent);

        Optional<Answer> answer = answerRepository.findById(noticeIdx);
        assertEquals("답변 테스트 내용", answer.get().getAnsContent());
    }

    @Test
    public void 답변_목록_조회() {
        String ansContent = "답변 목록 테스트 내용 고양이";

        for(int i = 0; i < 10; i++) {
            createAnswerMock(ansContent);
        }

        String findKeyword = "고양이";

        Page<Answer> answers = answerRepository.findByAnsContentContainingIgnoreCaseAndAnsActivatedTrueOrderByAnsIdxDesc(findKeyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "ansIdx")));

        // NumberOfElements 요청 페이지에서 조회 된 데이터의 갯수
        assertEquals(10, answers.getTotalElements());
    }

    @Test
    public void 인덱스로_답변_삭제() {
        String ansContent = "답변 테스트 내용";

        Long answerIdx = createAnswerMock(ansContent);

        Optional<Answer> answer = answerRepository.findById(answerIdx);

        assertTrue(answer.isPresent());

        answerRepository.deleteById(answerIdx);

        answer = answerRepository.findById(answerIdx);

        assertTrue(answer.isEmpty());
    }

    @Test
    public void 검색어로_답변_검색_리스트() {
        String ansContent = "답변 테스트 내용 고양이 병아리";

        Long answerIdx = createAnswerMock(ansContent);

        Optional<Answer> answer = answerRepository.findById(answerIdx);

        assertTrue(answer.isPresent());

        String findKeyword = "고양이";

        Page<Answer> search_answer = answerRepository.findByAnsContentContainingIgnoreCaseAndAnsActivatedTrueOrderByAnsIdxDesc(findKeyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "ansIdx")));

        for (Answer ans : search_answer) {
            assertEquals(ans.getAnsContent(),ansContent);
        }
    }
}
