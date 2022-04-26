package org.deco.gachicoding.api.notice.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.notice.dto.NoticeResponseDto;
import org.deco.gachicoding.api.notice.dto.NoticeSaveRequestDto;
import org.deco.gachicoding.api.notice.dto.NoticeUpdateRequestDto;
import org.deco.gachicoding.api.notice.service.NoticeService;
import org.deco.gachicoding.core.common.domain.Notice;
import org.deco.gachicoding.core.common.repository.NoticeRepository;
import org.deco.gachicoding.core.common.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Notice> findById(Long idx) {
        return noticeRepository.findById(idx);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeResponseDto findNoticeDetailById(Long idx) {
        Optional<Notice> notice = noticeRepository.findById(idx);
        NoticeResponseDto noticeDetail = NoticeResponseDto.builder()
                .notice(notice.get())
                .build();
        return noticeDetail;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeResponseDto> findNoticeByKeyword(String keyword, int page) {
        Page<Notice> notice = noticeRepository.findByNotContentContainingIgnoreCaseAndNotActivatedTrueOrNotTitleContainingIgnoreCaseAndNotActivatedTrueOrderByNotIdxDesc(keyword, keyword, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "notIdx")));
        Page<NoticeResponseDto> noticeList = notice.map(
                result -> new NoticeResponseDto(result)
        );
        return noticeList;
    }

    @Override
    @Transactional
    public Long registerNotice(NoticeSaveRequestDto dto) {
        Notice notice = dto.toEntity();

        // findById() -> 실제로 데이터베이스에 도달하고 실제 오브젝트 맵핑을 데이터베이스의 행에 리턴한다. 데이터베이스에 레코드가없는 경우 널을 리턴하는 것은 EAGER로드 한것이다.
        // getOne ()은 내부적으로 EntityManager.getReference () 메소드를 호출한다. 데이터베이스에 충돌하지 않는 Lazy 조작이다. 요청된 엔티티가 db에 없으면 EntityNotFoundException을 발생시킨다.
        notice.setUser(userRepository.getOne(dto.getUserIdx()));

        return noticeRepository.save(notice).getNotIdx();
    }

    @Override
    @Transactional
    public NoticeResponseDto updateNoticeById(Long idx, NoticeUpdateRequestDto dto) {

        Notice notice = findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + idx));

        notice = notice.update(dto.getNotTitle(), dto.getNotContent(), dto.getNotPin());

        NoticeResponseDto noticeDetail = NoticeResponseDto.builder()
                .notice(notice)
                .build();

        return noticeDetail;
    }

    @Override
    @Transactional
    public void disableNotice(Long idx) {
        Notice notice = findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + idx));

        notice.disableNotice();
    }

    @Override
    @Transactional
    public void enableNotice(Long idx) {
        Notice notice = findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + idx));

        notice.enableNotice();
    }

    @Override
    @Transactional
    public void deleteNoticeById(Long idx) {
        findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 글번호 = " + idx));

        noticeRepository.deleteById(idx);
    }
}
