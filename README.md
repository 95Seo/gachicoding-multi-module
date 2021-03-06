# 가치코딩 - Gachicoding
> 가치코딩 프로젝트

## 목차
* [리포지토리](#리포지토리)
* [제작 기간 & 참여 인원](#제작-기간-&-참여-인원)
* [사용 기술](#사용-기술)
* [프로젝트 구조도](#프로젝트-구조도)
* [ERD](#erd)
* [핵심 기능](#핵심-기능)
* [트러블 슈팅](#트러블-슈팅)

<br>

## 제작 기간 & 참여 인원
* 2022년 2월 25일 ~
* 참여 인원 : [김세현](https://github.com/saehyen), [김인표](https://github.com/kiminpyo), [김인환](https://github.com/inhwanK), [서영준](https://github.com/95Seo), [배지왕](https://github.com/BAE-JI-WANG)

<br>

## 리포지토리
* [백엔드 리포지토리(현재 리포지토리)](https://github.com/inhwanK/gachicoding)
* [프론트엔드 리포지토리]()
* [DevOps 리포지토리](https://github.com/BAE-JI-WANG/gachicoding_DevOps)

<br>

## 사용 기술
* Java 11 (jdk-11.0.13)
* gradle 7.4
* react 8.1.2
* aws
* terraform 1.1.7

<br>

## 프로젝트 구조도

<br>

## ERD

<br>

## 핵심 기능

<br>

## 트러블 슈팅
* [AWS Access Denied]() - IAM 계정으로 로그인 시 RDS 접근이 안되는 현상(작성 전)
* [Spring Security & OAuth2]() - 소셜 로그인 시, 기존의 일반 회원과 중복되는 아이디일 경우 로그인 연동(구글, 카카오, 깃허브) 처리(작성 전)
* [Spring Data JPA]() - not null 칼럼의 DynamicInsert,DynamicUpdate 어노테이션(작성 전)
* [CORS]() - CORS 정책 위반과 이를 해결화는 정확한 방법(작성 전)
* [UnexpectedRollbackException]() - 회원 가입 시 아이디(이메일) 중복처리에서 @Transactional 에 의한 롤백 예외 발생(작성 전)