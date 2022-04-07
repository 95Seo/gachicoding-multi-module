-- gachicoding
DROP SCHEMA IF EXISTS `gachicoding`;

-- gachicoding
CREATE SCHEMA `gachicoding`;

-- 유저
DROP TABLE IF EXISTS `gachicoding`.`user` RESTRICT;

-- 인증
DROP TABLE IF EXISTS `gachicoding`.`auth` RESTRICT;

-- 소셜
DROP TABLE IF EXISTS `gachicoding`.`social` RESTRICT;

-- 공지사항
DROP TABLE IF EXISTS `gachicoding`.`notice` RESTRICT;

-- 태그
DROP TABLE IF EXISTS `gachicoding`.`tag` RESTRICT;

-- 공지태그
DROP TABLE IF EXISTS `gachicoding`.`not_tag` RESTRICT;

-- 댓글
DROP TABLE IF EXISTS `gachicoding`.`comment` RESTRICT;

-- 가치고민(질문)
DROP TABLE IF EXISTS `gachicoding`.`gachiQ` RESTRICT;

-- 가치해결(답변)
DROP TABLE IF EXISTS `gachicoding`.`gachiA` RESTRICT;

-- 고민태그
DROP TABLE IF EXISTS `gachicoding`.`q_tag` RESTRICT;

-- 가치트렌드
DROP TABLE IF EXISTS `gachicoding`.`gachi_trend` RESTRICT;

-- 유저
CREATE TABLE `gachicoding`.`user`
(
    `user_idx`       BIGINT(20) UNSIGNED NOT NULL COMMENT '유저번호',               -- 유저번호
    `user_name`      VARCHAR(255)        NOT NULL COMMENT '유저이름',               -- 유저이름
    `user_nick`      VARCHAR(255)        NOT NULL COMMENT '유저별명',               -- 유저별명
    `user_email`     VARCHAR(255)        NOT NULL COMMENT '이메일',                -- 이메일
    `user_password`  VARCHAR(255)        NOT NULL COMMENT '비밀번호',               -- 비밀번호
    `user_regdate`   DATETIME            NOT NULL DEFAULT now() COMMENT '생성일자', -- 생성일자
    `user_activated` BOOLEAN             NOT NULL DEFAULT true COMMENT '활성상태',  -- 활성상태
    `user_role`      VARCHAR(15)         NOT NULL DEFAULT 'GUEST' COMMENT '권한', -- 권한
    `user_auth`      BOOLEAN             NOT NULL DEFAULT false COMMENT '인증여부', -- 인증여부
    `user_picture`   TEXT                NULL COMMENT '사진'                      -- 사진
)
    COMMENT '유저';

-- 유저
ALTER TABLE `gachicoding`.`user`
    ADD CONSTRAINT `PK_user` -- 유저 기본키
        PRIMARY KEY (
                     `user_idx` -- 유저번호
            );

-- 유저 유니크 인덱스
CREATE UNIQUE INDEX `UIX_user`
    ON `gachicoding`.`user` ( -- 유저
                             `user_nick` ASC, -- 유저별명
                             `user_email` ASC -- 이메일
        );

ALTER TABLE `gachicoding`.`user`
    MODIFY COLUMN `user_idx` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '유저번호';

-- 인증
CREATE TABLE `gachicoding`.`auth`
(
    `auth_token`   VARCHAR(37)  NOT NULL COMMENT '토큰',                 -- 토큰
    `auth_email`   VARCHAR(255) NOT NULL COMMENT '이메일',                -- 이메일
    `auth_regdate` DATETIME     NOT NULL DEFAULT now() COMMENT '생성일시', -- 생성일시
    `auth_expdate` DATETIME     NOT NULL COMMENT '만료일시'                -- 만료일시
)
    COMMENT '인증';

-- 인증
ALTER TABLE `gachicoding`.`auth`
    ADD CONSTRAINT `PK_auth` -- 인증 기본키
        PRIMARY KEY (
                     `auth_token` -- 토큰
            );

-- 소셜
CREATE TABLE `gachicoding`.`social`
(
    `social_idx`  BIGINT(21) UNSIGNED NOT NULL COMMENT '소셜번호',  -- 소셜번호
    `user_idx`    BIGINT(20) UNSIGNED NOT NULL COMMENT '유저번호',  -- 유저번호
    `social_type` VARCHAR(20)         NOT NULL COMMENT '소셜유형',  -- 소셜유형
    `social_id`   VARCHAR(255)        NOT NULL COMMENT '소셜아이디', -- 소셜아이디
    `social_date` DATETIME            NOT NULL COMMENT '인증일시'   -- 인증일시
)
    COMMENT '소셜';

-- 소셜
ALTER TABLE `gachicoding`.`social`
    ADD CONSTRAINT `PK_social` -- 소셜 기본키
        PRIMARY KEY (
                     `social_idx` -- 소셜번호
            );

ALTER TABLE `gachicoding`.`social`
    MODIFY COLUMN `social_idx` BIGINT(21) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '소셜번호';

-- 공지사항
CREATE TABLE `gachicoding`.`notice`
(
    `not_idx`       BIGINT(21) UNSIGNED NOT NULL COMMENT '공지사항번호',            -- 공지사항번호
    `user_idx`      BIGINT(20) UNSIGNED NOT NULL COMMENT '작성자번호',             -- 작성자번호
    `not_title`     VARCHAR(255)        NOT NULL COMMENT '제목',                -- 제목
    `not_content`   TEXT                NOT NULL COMMENT '본문',                -- 본문
    `not_pin`       BOOLEAN             NOT NULL COMMENT '고정',                -- 고정
    `not_regdate`   DATETIME            NOT NULL DEFAULT now() COMMENT '작성일', -- 작성일
    `not_activated` BOOLEAN             NOT NULL DEFAULT true COMMENT '활성상태'  -- 활성상태
)
    COMMENT '공지사항';

-- 공지사항
ALTER TABLE `gachicoding`.`notice`
    ADD CONSTRAINT `PK_notice` -- 공지사항 기본키
        PRIMARY KEY (
                     `not_idx` -- 공지사항번호
            );

ALTER TABLE `gachicoding`.`notice`
    MODIFY COLUMN `not_idx` BIGINT(21) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '공지사항번호';

-- 태그
CREATE TABLE `gachicoding`.`tag`
(
    `tag_idx`     BIGINT(21) UNSIGNED NOT NULL COMMENT '태그번호', -- 태그번호
    `tag_keyword` VARCHAR(255)        NOT NULL COMMENT '태그키워드' -- 태그키워드
)
    COMMENT '태그';

-- 태그
ALTER TABLE `gachicoding`.`tag`
    ADD CONSTRAINT `PK_tag` -- 태그 기본키
        PRIMARY KEY (
                     `tag_idx` -- 태그번호
            );

-- 공지태그
CREATE TABLE `gachicoding`.`not_tag`
(
    `not_idx` BIGINT(21) UNSIGNED NOT NULL COMMENT '공지사항번호', -- 공지사항번호
    `tag_idx` BIGINT(21) UNSIGNED NOT NULL COMMENT '태그번호'    -- 태그번호
)
    COMMENT '공지태그';

-- 공지태그
ALTER TABLE `gachicoding`.`not_tag`
    ADD CONSTRAINT `PK_not_tag` -- 공지태그 기본키
        PRIMARY KEY (
                     `not_idx`, -- 공지사항번호
                     `tag_idx` -- 태그번호
            );

-- 댓글
CREATE TABLE `gachicoding`.`comment`
(
    `comm_idx`         BIGINT(22) UNSIGNED NOT NULL COMMENT '댓글번호',   -- 댓글번호
    `user_idx`         BIGINT(20) UNSIGNED NOT NULL COMMENT '작성자번호',  -- 작성자번호
    `parents_idx`      BIGINT(22) UNSIGNED NOT NULL COMMENT '상위댓글번호', -- 상위댓글번호
    `comm_content`     VARCHAR(9999)       NOT NULL COMMENT '댓글내용',   -- 댓글내용
    `comm_regdate`     DATETIME            NOT NULL COMMENT '작성일시',   -- 작성일시
    `comm_activate`    BOOLEAN             NOT NULL COMMENT '활성여부',   -- 활성여부
    `article_category` VARCHAR(255)        NOT NULL COMMENT '게시글분류',  -- 게시글분류
    `article_idx`      BIGINT(23) UNSIGNED NOT NULL COMMENT '글번호'     -- 글번호
)
    COMMENT '댓글';

-- 댓글
ALTER TABLE `gachicoding`.`comment`
    ADD CONSTRAINT `PK_comment` -- 댓글 기본키
        PRIMARY KEY (
                     `comm_idx` -- 댓글번호
            );

-- 가치고민(질문)
CREATE TABLE `gachicoding`.`gachiQ`
(
    `q_idx`      BIGINT(22) UNSIGNED NOT NULL COMMENT '가치고민번호', -- 가치고민번호
    `user_idx`   BIGINT(20) UNSIGNED NOT NULL COMMENT '작성자번호',  -- 작성자번호
    `q_title`    VARCHAR(255)        NOT NULL COMMENT '가치고민제목', -- 가치고민제목
    `q_content`  TEXT                NOT NULL COMMENT '가치고민내용', -- 가치고민내용
    `q_error`    TEXT                NULL COMMENT '가치고민에러',     -- 가치고민에러
    `q_category` VARCHAR(30)         NOT NULL COMMENT '카테고리',   -- 카테고리
    `q_solve`    BOOLEAN             NOT NULL COMMENT '해결여부',   -- 해결여부
    `q_regdate`  DATETIME            NOT NULL COMMENT '작성일',    -- 작성일
    `q_activate` BOOLEAN             NOT NULL COMMENT '활성여부'    -- 활성여부
)
    COMMENT '가치고민(질문)';

-- 가치고민(질문)
ALTER TABLE `gachicoding`.`gachiQ`
    ADD CONSTRAINT `PK_gachiQ` -- 가치고민(질문) 기본키
        PRIMARY KEY (
                     `q_idx` -- 가치고민번호
            );

-- 가치해결(답변)
CREATE TABLE `gachicoding`.`gachiA`
(
    `a_idx`      BIGINT(23) UNSIGNED NOT NULL COMMENT '가치해결번호', -- 가치해결번호
    `user_idx`   BIGINT(20) UNSIGNED NOT NULL COMMENT '작성자번호',  -- 작성자번호
    `q_idx`      BIGINT(22) UNSIGNED NOT NULL COMMENT '가치고민번호', -- 가치고민번호
    `a_content`  TEXT                NOT NULL COMMENT '가치해결내용', -- 가치해결내용
    `a_select`   BOOLEAN             NOT NULL COMMENT '채택여부',   -- 채택여부
    `a_regdate`  DATETIME            NOT NULL COMMENT '작성일',    -- 작성일
    `a_activate` BOOLEAN             NOT NULL COMMENT '활성여부'    -- 활성여부
)
    COMMENT '가치해결(답변)';

-- 가치해결(답변)
ALTER TABLE `gachicoding`.`gachiA`
    ADD CONSTRAINT `PK_gachiA` -- 가치해결(답변) 기본키
        PRIMARY KEY (
                     `a_idx` -- 가치해결번호
            );

-- 고민태그
CREATE TABLE `gachicoding`.`q_tag`
(
    `q_idx`   BIGINT(22) UNSIGNED NOT NULL COMMENT '가치고민번호', -- 가치고민번호
    `tag_idx` BIGINT(21) UNSIGNED NOT NULL COMMENT '태그번호'    -- 태그번호
)
    COMMENT '고민태그';

-- 고민태그
ALTER TABLE `gachicoding`.`q_tag`
    ADD CONSTRAINT `PK_q_tag` -- 고민태그 기본키
        PRIMARY KEY (
                     `q_idx`, -- 가치고민번호
                     `tag_idx` -- 태그번호
            );

-- 가치트렌드
CREATE TABLE `gachicoding`.`gachi_trend`
(
    `news_idx`       BIGINT(20) UNSIGNED NOT NULL COMMENT '뉴스번호', -- 뉴스번호
    `user_idx`       BIGINT(20) UNSIGNED NULL COMMENT '작성자번호',    -- 작성자번호
    `news_title`     VARCHAR(255)        NULL COMMENT '뉴스제목',     -- 뉴스제목
    `news_content`   TEXT                NULL COMMENT '뉴스내용',     -- 뉴스내용
    `news_ref`       TEXT                NULL COMMENT '출처',       -- 출처
    `news_regdate`   DATETIME            NOT NULL COMMENT '작성일',  -- 작성일
    `news_url`       VARCHAR(255)        NULL COMMENT '링크',       -- 링크
    `news_pin`       BOOLEAN             NOT NULL COMMENT '고정',   -- 고정
    `news_thumbnail` VARCHAR(255)        NOT NULL COMMENT '썸네일'   -- 썸네일
)
    COMMENT '가치트렌드';

-- 가치트렌드
ALTER TABLE `gachicoding`.`gachi_trend`
    ADD CONSTRAINT `PK_gachi_trend` -- 가치트렌드 기본키
        PRIMARY KEY (
                     `news_idx` -- 뉴스번호
            );

-- 소셜
ALTER TABLE `gachicoding`.`social`
    ADD CONSTRAINT `FK_user_TO_social` -- 유저 -> 소셜
        FOREIGN KEY (
                     `user_idx` -- 유저번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );

-- 공지사항
ALTER TABLE `gachicoding`.`notice`
    ADD CONSTRAINT `FK_user_TO_notice` -- 유저 -> 공지사항
        FOREIGN KEY (
                     `user_idx` -- 작성자번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );

-- 공지태그
ALTER TABLE `gachicoding`.`not_tag`
    ADD CONSTRAINT `FK_notice_TO_not_tag` -- 공지사항 -> 공지태그
        FOREIGN KEY (
                     `not_idx` -- 공지사항번호
            )
            REFERENCES `gachicoding`.`notice` ( -- 공지사항
                                               `not_idx` -- 공지사항번호
                );

-- 공지태그
ALTER TABLE `gachicoding`.`not_tag`
    ADD CONSTRAINT `FK_tag_TO_not_tag` -- 태그 -> 공지태그
        FOREIGN KEY (
                     `tag_idx` -- 태그번호
            )
            REFERENCES `gachicoding`.`tag` ( -- 태그
                                            `tag_idx` -- 태그번호
                );

-- 댓글
ALTER TABLE `gachicoding`.`comment`
    ADD CONSTRAINT `FK_comment_TO_comment` -- 댓글 -> 댓글
        FOREIGN KEY (
                     `parents_idx` -- 상위댓글번호
            )
            REFERENCES `gachicoding`.`comment` ( -- 댓글
                                                `comm_idx` -- 댓글번호
                );

-- 댓글
ALTER TABLE `gachicoding`.`comment`
    ADD CONSTRAINT `FK_user_TO_comment` -- 유저 -> 댓글
        FOREIGN KEY (
                     `user_idx` -- 작성자번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );

-- 가치고민(질문)
ALTER TABLE `gachicoding`.`gachiQ`
    ADD CONSTRAINT `FK_user_TO_gachiQ` -- 유저 -> 가치고민(질문)
        FOREIGN KEY (
                     `user_idx` -- 작성자번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );

-- 가치해결(답변)
ALTER TABLE `gachicoding`.`gachiA`
    ADD CONSTRAINT `FK_user_TO_gachiA` -- 유저 -> 가치해결(답변)
        FOREIGN KEY (
                     `user_idx` -- 작성자번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );

-- 가치해결(답변)
ALTER TABLE `gachicoding`.`gachiA`
    ADD CONSTRAINT `FK_gachiQ_TO_gachiA` -- 가치고민(질문) -> 가치해결(답변)
        FOREIGN KEY (
                     `q_idx` -- 가치고민번호
            )
            REFERENCES `gachicoding`.`gachiQ` ( -- 가치고민(질문)
                                               `q_idx` -- 가치고민번호
                );

-- 고민태그
ALTER TABLE `gachicoding`.`q_tag`
    ADD CONSTRAINT `FK_gachiQ_TO_q_tag` -- 가치고민(질문) -> 고민태그
        FOREIGN KEY (
                     `q_idx` -- 가치고민번호
            )
            REFERENCES `gachicoding`.`gachiQ` ( -- 가치고민(질문)
                                               `q_idx` -- 가치고민번호
                );

-- 고민태그
ALTER TABLE `gachicoding`.`q_tag`
    ADD CONSTRAINT `FK_tag_TO_q_tag` -- 태그 -> 고민태그
        FOREIGN KEY (
                     `tag_idx` -- 태그번호
            )
            REFERENCES `gachicoding`.`tag` ( -- 태그
                                            `tag_idx` -- 태그번호
                );

-- 가치트렌드
ALTER TABLE `gachicoding`.`gachi_trend`
    ADD CONSTRAINT `FK_user_TO_gachi_trend` -- 유저 -> 가치트렌드
        FOREIGN KEY (
                     `user_idx` -- 작성자번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `user_idx` -- 유저번호
                );