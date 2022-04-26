package org.deco.gachicoding.api.notice;

import org.deco.gachicoding.api.notice.dto.NoticeResponseDto;
import org.deco.gachicoding.api.notice.dto.NoticeSaveRequestDto;
import org.deco.gachicoding.api.notice.dto.NoticeUpdateRequestDto;
import org.deco.gachicoding.api.notice.service.NoticeService;
import org.deco.gachicoding.core.common.domain.Notice;
import org.deco.gachicoding.infrastructure.web.GachicodingWebApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GachicodingWebApplication.class)
public class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    Long noticeIdx;

    String notTitle = "공지사항 테스트 제목 고양이 병아리";
    String notContent = "공지사항 테스트 내용 강아지 병아리";
    Boolean notPin = false;

    @BeforeEach
    void before() {
        NoticeSaveRequestDto entity = NoticeSaveRequestDto.builder()
                .userIdx(Long.valueOf(1))
                .notTitle(notTitle)
                .notContent(notContent)
                .notPin(notPin)
                .build();

        noticeIdx = noticeService.registerNotice(entity);
    }

    @AfterEach
    void after() {
        if (noticeIdx != null) {
            noticeService.deleteNoticeById(this.noticeIdx);
        }
        noticeIdx = null;
    }

    @Test
    void 공지사항_작성() {
        Optional<Notice> notice = noticeService.findById(noticeIdx);

        assertEquals(notTitle, notice.get().getNotTitle());
        assertEquals(notContent, notice.get().getNotContent());
    }

    @Test
    public void 공지사항_리스트_조회() {
        String keyword = "병아리";

        Page<NoticeResponseDto> listNotice = noticeService.findNoticeByKeyword(keyword, 0);

        assertNotEquals(listNotice.getTotalElements(), 1);
    }

    // 같은 비즈니스 로직의 다른 사용법을 테스트 케이스로 작성한 것..
    // 필요한 테스트 케이스 일까(통합 시켜도 될까)?
    @Test
    public void 검색어로_공지사항_검색() {
        String keyword = "병아리";

        Page<NoticeResponseDto> searchNotice = noticeService.findNoticeByKeyword(keyword, 0);
        assertEquals(searchNotice.getContent().get(0).getNotTitle(), notTitle);
        assertEquals(searchNotice.getContent().get(0).getNotContent(), notContent);
    }

    @Test
    public void 인덱스로_공지사항_수정() {
        String updateTitle = "공지사항 수정된 테스트 제목";
        String updateContent = "공지사항 수정된 테스트 내용";
        Boolean updatePin = true;

        NoticeUpdateRequestDto updateNotice = new NoticeUpdateRequestDto(updateTitle, updateContent, updatePin);

        noticeService.updateNoticeById(noticeIdx, updateNotice);

        Notice notice = noticeService.findById(noticeIdx).get();

        assertNotEquals(notTitle, notice.getNotTitle());
        assertEquals(updateTitle, notice.getNotTitle());

        assertNotEquals(notContent, notice.getNotContent());
        assertEquals(updateContent, notice.getNotContent());

        assertNotEquals(notPin, notice.getNotPin());
        assertEquals(updatePin, notice.getNotPin());
    }

    @Test
    public void 인덱스로_공지사항_비활성화() {
        noticeService.disableNotice(noticeIdx);

        Optional<Notice> notice = noticeService.findById(noticeIdx);

        assertEquals(notice.get().getNotActivated(), false);
    }

    @Test
    public void 인덱스로_공지사항_활성화() {
        noticeService.enableNotice(noticeIdx);

        Optional<Notice> notice = noticeService.findById(noticeIdx);

        assertEquals(notice.get().getNotActivated(), true);
    }

    @Test
    public void 공지사항_삭제() {
        noticeService.deleteNoticeById(noticeIdx);
        assertTrue(noticeService.findById(noticeIdx).isEmpty());
        noticeIdx = null;
    }
}
