package org.deco.gachicoding.core.common.repository;

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
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    private Long createQuestionMock(String queTitle, String queContent, String queError, String queCategory) {
        Long userIdx = Long.valueOf(1);

        User user = User.builder()
                .userIdx(userIdx)
                .build();

        Question entity = Question.builder()
                .user(user)
                .queTitle(queTitle)
                .queContent(queContent)
                .queError(queError)
                .queCategory(queCategory)
                .build();

        return questionRepository.save(entity).getQueIdx();
    }

    @Test
    public void 인덱스로_질문_조회() {
        String queTitle = "질문 테스트 제목";
        String queContent = "질문 테스트 내용";
        String queError = "질문 테스트 에러 소스";
        String queCategory = "자바";

        Long questionIdx = createQuestionMock(queTitle, queContent, queError, queCategory);

        Optional<Question> question = questionRepository.findById(questionIdx);
        assertEquals("질문 테스트 제목", question.get().getQueTitle());
        assertEquals("질문 테스트 내용", question.get().getQueContent());
    }

    @Test
    public void 질문_목록_조회() {
        String queTitle = "질문 목록 테스트 제목 고양이";
        String queContent = "질문 목록 테스트 내용";
        String queError = "질문 테스트 에러 소스";
        String queCategory = "자바";

        for(int i = 0; i < 10; i++) {
            Long questionIdx = createQuestionMock(queTitle, queContent, queError, queCategory);
        }

        String findKeyword = "고양이";

        Page<Question> questions = questionRepository.findByQueContentContainingIgnoreCaseAndQueActivatedTrueOrQueTitleContainingIgnoreCaseAndQueActivatedTrueOrderByQueIdxDesc(findKeyword, findKeyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "queIdx")));

        // NumberOfElements 요청 페이지에서 조회 된 데이터의 갯수
        assertEquals(10, questions.getTotalElements());
    }

    @Test
    public void 인덱스로_질문_삭제() {
        String queTitle = "질문 테스트 제목";
        String queContent = "질문 테스트 내용";
        String queError = "질문 테스트 에러 소스";
        String queCategory = "자바";

        Long questionIdx = createQuestionMock(queTitle, queContent, queError, queCategory);

        Optional<Question> question = questionRepository.findById(questionIdx);

        assertTrue(question.isPresent());

        questionRepository.deleteById(questionIdx);

        question = questionRepository.findById(questionIdx);

        assertTrue(question.isEmpty());
    }

    @Test
    public void 검색어로_질문_검색_리스트() {
        String queTitle = "질문 테스트 제목 고양이 병아리";
        String queContent = "질문 테스트 내용 강아지 병아리";
        String queError = "질문 테스트 에러 소스";
        String queCategory = "자바";

        Long questionIdx = createQuestionMock(queTitle, queContent, queError, queCategory);

        Optional<Question> question = questionRepository.findById(questionIdx);

        assertTrue(question.isPresent());

        String findKeyword = "고양이";

        Page<Question> search_question = questionRepository.findByQueContentContainingIgnoreCaseAndQueActivatedTrueOrQueTitleContainingIgnoreCaseAndQueActivatedTrueOrderByQueIdxDesc(findKeyword, findKeyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "queIdx")));

        for (Question que : search_question) {
            assertEquals(que.getQueTitle(),queTitle);
            assertEquals(que.getQueContent(),queContent);
            assertEquals(que.getQueError(),queError);
            assertEquals(que.getQueCategory(),queCategory);
        }
    }
}
