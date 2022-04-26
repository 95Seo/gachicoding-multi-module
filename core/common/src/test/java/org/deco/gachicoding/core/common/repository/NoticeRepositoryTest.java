package org.deco.gachicoding.core.common.repository;

import org.deco.gachicoding.core.common.domain.Notice;
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
public class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    private Long createNoticeMock(String notTitle, String notContent, Boolean notPin, Boolean notActivated) {
        Long userIdx = Long.valueOf(1);

        User user = User.builder()
                .userIdx(userIdx)
                .build();

        Notice entity = Notice.builder()
                .user(user)
                .notTitle(notTitle)
                .notContent(notContent)
                .notPin(notPin)
                .notActivated(notActivated)
                .build();

        return noticeRepository.save(entity).getNotIdx();
    }

    @Test
    public void 인덱스로_공지사항_조회() {
        String notTitle = "공지사항 테스트 제목";
        String notContent = "공지사항 테스트 내용";
        Boolean notPin = false;
        Boolean notActivated = true;
        Long noticeIdx = createNoticeMock(notTitle, notContent, notPin, notActivated);

        Optional<Notice> notice = noticeRepository.findById(noticeIdx);
        assertEquals("공지사항 테스트 제목", notice.get().getNotTitle());
        assertEquals("공지사항 테스트 내용", notice.get().getNotContent());
    }

    @Test
    public void 공지사항_목록_조회() {
        String notTitle = "공지사항 목록 테스트 제목 고양이";
        String notContent = "공지사항 목록 테스트 내용";
        Boolean notPin = false;
        Boolean notActivated = true;
        for(int i = 0; i < 10; i++) {
            createNoticeMock(notTitle, notContent, notPin, notActivated);
        }

        String keyword = "고양이";

        Page<Notice> notice = noticeRepository.findByNotContentContainingIgnoreCaseAndNotActivatedTrueOrNotTitleContainingIgnoreCaseAndNotActivatedTrueOrderByNotIdxDesc(keyword, keyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "notIdx")));

        // NumberOfElements 요청 페이지에서 조회 된 데이터의 갯수
        assertEquals(10, notice.getTotalElements());
    }

    @Test
    public void 인덱스로_공지사항_삭제() {
        String notTitle = "공지사항 테스트 제목";
        String notContent = "공지사항 테스트 내용";
        Boolean notPin = false;
        Boolean notActivated = true;
        Long noticeIdx = createNoticeMock(notTitle, notContent, notPin, notActivated);

        Optional<Notice> notice = noticeRepository.findById(noticeIdx);

        assertTrue(notice.isPresent());

        noticeRepository.deleteById(noticeIdx);

        notice = noticeRepository.findById(noticeIdx);

        assertTrue(notice.isEmpty());
    }

    @Test
    public void 검색어로_공지사항_검색_리스트() {
        String notTitle = "공지사항 테스트 제목 고양이 병아리";
        String notContent = "공지사항 테스트 내용 강아지 병아리";
        Boolean notPin = false;
        Boolean notActivated = true;
        Long noticeIdx = createNoticeMock(notTitle, notContent, notPin, notActivated);

        Optional<Notice> notice = noticeRepository.findById(noticeIdx);

        assertTrue(notice.isPresent());

        String toFindKeyword = "고양이";

        Page<Notice> search_notice = noticeRepository.findByNotContentContainingIgnoreCaseAndNotActivatedTrueOrNotTitleContainingIgnoreCaseAndNotActivatedTrueOrderByNotIdxDesc(toFindKeyword, toFindKeyword, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "notIdx")));

        for (Notice n : search_notice) {
            assertEquals(n.getNotTitle(),notTitle);
            assertEquals(n.getNotContent(),notContent);
            assertEquals(n.getNotPin(),notPin);
            assertEquals(n.getNotActivated(),notActivated);
        }
    }

}
