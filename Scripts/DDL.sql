-- 유저
DROP TABLE IF EXISTS `gachicoding`.`user` RESTRICT;

-- 이메일토큰
DROP TABLE IF EXISTS `gachicoding`.`email_token` RESTRICT;

-- 소셜인증
DROP TABLE IF EXISTS `gachicoding`.`social_auth` RESTRICT;

-- gachicoding
DROP SCHEMA IF EXISTS `gachicoding`;

-- gachicoding
CREATE SCHEMA `gachicoding`;

-- 유저
CREATE TABLE `gachicoding`.`user` (
                                      `idx`            BIGINT(20) UNSIGNED NOT NULL COMMENT '유저번호', -- 유저번호
                                      `name`           VARCHAR(255)        NOT NULL COMMENT '유저이름', -- 유저이름
                                      `email`          VARCHAR(255)        NOT NULL COMMENT '이메일', -- 이메일
                                      `password`       VARCHAR(255)        NOT NULL COMMENT '비밀번호', -- 비밀번호
                                      `regdate`        DATETIME            NOT NULL COMMENT '생성일자', -- 생성일자
                                      `activated`      BOOLEAN             NOT NULL COMMENT '활성상태', -- 활성상태
                                      `role`           VARCHAR(15)         NOT NULL COMMENT '권한', -- 권한
                                      `authentication` BOOLEAN             NOT NULL COMMENT '인증여부' -- 인증여부
)
    COMMENT '유저';

-- 유저
ALTER TABLE `gachicoding`.`user`
    ADD CONSTRAINT `PK_user` -- 유저 기본키
        PRIMARY KEY (
                     `idx` -- 유저번호
            );

ALTER TABLE `gachicoding`.`user`
    MODIFY COLUMN `idx` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '유저번호';

-- 이메일토큰
CREATE TABLE `gachicoding`.`email_token` (
                                             `token`           VARCHAR(255) NOT NULL COMMENT '토큰', -- 토큰
                                             `email`           VARCHAR(255) NOT NULL COMMENT '이메일', -- 이메일
                                             `regdate`         DATETIME     NOT NULL COMMENT '생성일시', -- 생성일시
                                             `expiration_date` DATETIME     NOT NULL COMMENT '만료일시' -- 만료일시
)
    COMMENT '이메일토큰';

-- 이메일토큰
ALTER TABLE `gachicoding`.`email_token`
    ADD CONSTRAINT `PK_email_token` -- 이메일토큰 기본키
        PRIMARY KEY (
                     `token` -- 토큰
            );

-- 소셜인증
CREATE TABLE `gachicoding`.`social_auth` (
                                             `idx`       BIGINT(21) UNSIGNED NOT NULL COMMENT '소셜인증번호', -- 소셜인증번호
                                             `user_idx`  BIGINT(20) UNSIGNED NOT NULL COMMENT '유저번호', -- 유저번호
                                             `type`      VARCHAR(20)         NOT NULL COMMENT '소셜유형', -- 소셜유형
                                             `social_id` VARCHAR(255)        NOT NULL COMMENT '소셜아이디', -- 소셜아이디
                                             `auth_date` DATETIME            NOT NULL COMMENT '인증일시' -- 인증일시
)
    COMMENT '소셜인증';

-- 소셜인증
ALTER TABLE `gachicoding`.`social_auth`
    ADD CONSTRAINT `PK_social_auth` -- 소셜인증 기본키
        PRIMARY KEY (
                     `idx` -- 소셜인증번호
            );

-- 소셜인증
ALTER TABLE `gachicoding`.`social_auth`
    ADD CONSTRAINT `FK_user_TO_social_auth` -- 유저 -> 소셜인증
        FOREIGN KEY (
                     `user_idx` -- 유저번호
            )
            REFERENCES `gachicoding`.`user` ( -- 유저
                                             `idx` -- 유저번호
                );