package org.deco.gachicoding.service.notice;

import org.deco.gachicoding.domain.notice.Notice;
import org.deco.gachicoding.dto.notice.NoticeDetailResponseDto;
import org.deco.gachicoding.dto.notice.NoticeListResponseDto;
import org.deco.gachicoding.dto.notice.NoticeSaveRequestDto;
import org.deco.gachicoding.dto.notice.NoticeUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface NoticeService {
    Optional<Notice> findById(Long idx);

    NoticeDetailResponseDto findNoticeDetailById(Long idx);

    Page<NoticeListResponseDto> findNoticeByKeyword(String keyword, int page);

    Long registerNotice(NoticeSaveRequestDto dto);

    NoticeDetailResponseDto updateNoticeById(Long idx, NoticeUpdateRequestDto dto);

    Long deleteNoticeById(Long idx);
}
