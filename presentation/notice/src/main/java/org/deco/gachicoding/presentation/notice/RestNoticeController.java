package org.deco.gachicoding.presentation.notice;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.notice.dto.NoticeResponseDto;
import org.deco.gachicoding.api.notice.dto.NoticeSaveRequestDto;
import org.deco.gachicoding.api.notice.dto.NoticeUpdateRequestDto;
import org.deco.gachicoding.api.notice.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestNoticeController {

    private final NoticeService noticeService;

    @ApiOperation(value = "공지사항 상세 보기")
    @GetMapping("/notice/detail/{idx}")
    public NoticeResponseDto findNoticeDetailById(@PathVariable Long idx) {
        return noticeService.findNoticeDetailById(idx);
    }

    @ApiOperation(value = "공지사항 리스트 보기")
    @GetMapping("/notice/list/{page}")
    public Page<NoticeResponseDto> findNoticeByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword, @PathVariable int page) {
        return noticeService.findNoticeByKeyword(keyword, page);
    }

    @ApiOperation(value = "공지사항 등록")
    @PostMapping("/notice")
    public Long registerNotice(@RequestBody NoticeSaveRequestDto dto) {
        return noticeService.registerNotice(dto);
    }

    @ApiOperation(value = "공지사항 수정")
    @PutMapping("/notice/update/{idx}")
    public NoticeResponseDto updateNotice(@PathVariable Long idx, @RequestBody NoticeUpdateRequestDto dto){
        return noticeService.updateNoticeById(idx, dto);
    }

    @ApiOperation(value = "공지사항 비활성화")
    @PutMapping("/notice/disable/{idx}")
    public void disableNotice(@PathVariable Long idx){
        noticeService.disableNotice(idx);
    }

    @ApiOperation(value = "공지사항 활성화")
    @PutMapping("/notice/enable/{idx}")
    public void enableNotice(@PathVariable Long idx){
        noticeService.enableNotice(idx);
    }

    @ApiOperation(value = "공지사항 삭제")
    @DeleteMapping("/notice/delete/{idx}")
    public void deleteNotice(@PathVariable Long idx){
        noticeService.deleteNoticeById(idx);
    }

}
