
# 개발 인원 이하 총 4명
    - 장세존 : 프로젝트 총괄 및 Flask 와 Pandas 를 이용한 데이터 분석 담당
    - 이준강 : 프론트 템플릿 설계 및 지점 정보 확인 및 수정 관리, 주문 내역 관리 기능 담당
    - 이태훈 : 프로젝트 설계, 제안서 및 PPT 문서 작성 담당
    - 임승택 : 빅데이터 분석을 위한 예제 데이터셋 설계 및 알고리즘 개발, 주문 내역 관리 및 프론트 설계 담당

# 개발 내역
## 22.11.08
- rest api 이용 데이터 insert 확인
- data 가져와서 테이블 보여주기 확인
- data 가져와서 차트 보여주기 화인

## 22.11.09
- DB 테이블에 맞게 Entity 추가, 수정

## 22.11.10
- navi 지정 및 view 화면 사용은 chart1.html 파일을 참고하세요.

## 22.11.11
- branch api insert 요청, 목록 확인, 승인 버튼 누를 시 mail 발송 및 계정활성화

## 22.11.13
- table 수정 반영
- flask 와 연동을 위한 controller 및 component 생성 => FlaskController, FlaskRequest
- chart2 : bar&line&평균선 chart 개발
- chart3 : 한국 지도 chart 개발 => googleChart 이용

## 22.11.14
- 제너레이터 변경: db 변경 시 branch를 먼저 생성해서 데이터를 넣고 item_id의 범위를 확인해서 ajax.html에 수정해 주어야 한다.
- 시큐리티 로그인 설정
- 매출 페이지 페이징 처리

## 22.11.17
- fragments/header.html => 검색창, 이름
- fragments/navi.html => 사이드바
- templates/main.html => 대시보드
- login/login.html => 로그인

- PM20:00 ~
- 로컬 접속 시 로그인 화면으로 보이게 수정 완료.
- 
~~- 로그아웃 시 로그인 화면으로 돌아기지 않음 config, contoller 확인 요망.~~
- loginformsub.html => login.html으로 수정.
- 로고 사진(코끼티) 수정 완료.
- 사이드바 색상 변경 완료. (blue -> middle gray)
- 원본 파일들은 /templates/original 폴더로 옮김.
- test 폴더 삭제할 것. (원본 파일은 기존에 있었던 소스파일들 의미함 위에 원본 폴더 위치 참고)

## 22.11.18
- 주문 내역 상세 & 지점관리 테이블 수정 완료 (페이징, 검색 기능 미구현)
- css, js 디렉토리 다시 정리했으나 가급적이면 git pull 보다는 새 프로젝트로 불러오시는게 권장함.
- 12:05 로그아웃 에러 해결 완료.. (장세존님 해결)
- 
~~- 메인페이지 => 한줄 지점 수, 월간 총 매출액, 일간 총 매출액~~
- 로그인 처음 접속 시 스크립트 접속 차단, 외부 문서 접속 차단 완료

- 로그인 처음 접속 시 스크립트 보이는 현상 문제 해결완료.

## 22.11.18 세존
- 관리자 메인 페이지 대시보드 수정 : 지도 위치 조절, 대시보드 겹치는 문제 수정

## 22.11.19 준강
**[삭제해야할 것]**
- tables.html (위치 : branch/tables.html)
- common (위치 : templates/common)

**[소스파일명 수정 참고]**
- mainsub.html => main.html

**[추후 푸시 예정]**
- list.html - 매출 리스트 (페이징, 테이블 간격 조정, 그래프 추가)
- branchManage.html - 지점 관리 (페이징, 검색 기능)
- 패스워드 변경 시 DB 암호화 확인

---
- 로그인 에러 시 이미지와 폰트 비정상적으로 출력되는 문제 해결 완료.
- main.html => 한줄 요약 : 일•주•월간 총 매출액 등등 추가 완료. (주석 참고..)
- branchUpdate.html(지점 정보 수정) - 지점명 수정 불가 완료.
---

## 22.11.20 준강
- list.html(주문 내역 상세) - 테이블 일부 간격 조정, 페이징 구현 완료.
- branchManage.html(지점관리) - 페이징 구현 완료.
- layout1sub.html - Table 관련 JS 주석 처리.

**[추후 푸시 예정]**
- list.html - 매출 리스트 (그래프 추가)
- branchManage.html - 지점 관리 (검색 기능)
- 패스워드 변경 시 DB 암호화 확인
- main.html(메인 대시보드) - 지점 수 아이콘 변경

## 22.11.23 세존
- flask 와 연동 최종
- 개발 단계 최종 
- 추후 추가 예정 기능
  - 지점 로그인 후 지점 정보 수정 기능
  - 페이지 로딩 속도 개선
  - 분석 예측 그래프 생성(python 이용)
- 미구현 내용, 오류 내용 : 추후 수정 예정
  1. 헤더 - '설정'(정보수정) 미구현

  2. 메인 통계(본사) - 접근 시
     - 지점 매출 순위 TOP 10
       --> 화면 축소 시 그래프 레이아웃 이탈.

     - 지리적 매출 분석
       --> 화면 확대 시 지도 레이아웃 이탈

  3. [주문 내역 상세 ] 리스트 불러오는데 로딩 5초 지연

  4. [일별 통계]
     - 일별 매출 합계(지점 총합) - 확대 시 그래프 레이아웃 이탈
     - 파이썬 통계 불러오는데 약 35초 지연

  5. [월별 통계]
     - 파이썬 통계 불러오는데 약 30초 지연

## 22.11.24
- 디버깅 시작
- mainFinal_221124 브렌치는 11.23 으로 완료된 프로젝트 코드 브렌치입니다.
- 혹시라도 있을 사고에 대비해서 백업 브렌치이기 때문에 일반적인 수정 사항은 main 브렌치를 기준으로 머지해주시면 됩니다.
  - 즉 mainFinal_221124 는 백업용 브렌치이니 건들지말아주세요!!

## 22.11.29
- 메일 발송 기능 수정 완료