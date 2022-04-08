package org.deco.gachicoding.dto.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDetailResponseDto {

    private Long notIdx;
    private Long userIdx;
    private String userNick;
    private String userPicture;
    private String notTitle;
    private String notContent;
    private int notViews;
    private Boolean notPin;
    private LocalDateTime notRegdate;

    @Builder
    public NoticeDetailResponseDto(Long notIdx, Long userIdx, String userNick, String userPicture, String notTitle, String notContent, Boolean notPin, LocalDateTime notRegdate) {
        this.notIdx = notIdx;
        this.userIdx = userIdx;
        this.userNick = userNick;
        this.userPicture = userPicture;
        this.notTitle = notTitle;
        this.notContent = notContent;
        this.notPin = notPin;
        this.notRegdate = notRegdate;
    }
}
