package org.deco.gachicoding.api.notice.service;

import org.deco.gachicoding.api.notice.dto.NoticeResponseDto;
import org.deco.gachicoding.api.notice.dto.NoticeSaveRequestDto;
import org.deco.gachicoding.api.notice.dto.NoticeUpdateRequestDto;
import org.deco.gachicoding.core.common.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface NoticeService {
    Optional<Notice> findById(Long idx);

    NoticeResponseDto findNoticeDetailById(Long idx);

    Page<NoticeResponseDto> findNoticeByKeyword(String keyword, int page);

    Long registerNotice(NoticeSaveRequestDto dto);

    NoticeResponseDto updateNoticeById(Long idx, NoticeUpdateRequestDto dto);

    void disableNotice(Long idx);

    void enableNotice(Long idx);

    void deleteNoticeById(Long idx);
}
