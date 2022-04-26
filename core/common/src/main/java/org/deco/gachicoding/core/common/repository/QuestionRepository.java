package org.deco.gachicoding.core.common.repository;

import org.deco.gachicoding.core.common.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Containing이 없다면 해당 키워드와 일치하는 결과만 찾고, 이 키워드가 있는 경우는 포함하는 결과를 검색 즉, SQL문의 like %xx% 와 비슷함
    // IgnoreCase 키워드는 대소문자 구별을 하지 않는다는 의미, 없다면 대소문자 구별
    Page<Question> findByQueContentContainingIgnoreCaseAndQueActivatedTrueOrQueTitleContainingIgnoreCaseAndQueActivatedTrueOrderByQueIdxDesc(String queContent, String queTitle, Pageable pageable);

}