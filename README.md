# :clapper:ottsharing
## :pushpin: 개요
ottsharing은 OTT 계정 공유 중개 서비스입니다. 파티장이 계정을 공유하는 파티를 개설하면, 파티원 최대 3명이 공유받을 수 있게끔 중개합니다.
Java와 Spring Boot를 이용해 REST API 서버를 만들어보는걸 목표로 제작되었습니다.

## :pushpin: 목표 및 사용 기술 스택 
<img src="https://img.shields.io/badge/Spring%20Boot-2.6.4-yellow"> <img src = "https://img.shields.io/badge/DataBase-MySQL-blue?logo=MySQL&logoColor=blue"> <img src = "https://img.shields.io/badge/Code%20Style-Naver%20CheckStyle-brightgreen?logo=naver&logoColor=brightgreen"> <img src="https://img.shields.io/badge/DataAccess-Spring%20JPA-lightgrey">
* OTT 계정 공유 중개 서비스를 구현해 내는 것이 목표입니다.
* 이유와 근거가 명확한 기술의 사용을 지향합니다.
* 이 프로젝트를 통해 Spring Web/JPA 등을 사용하고 REST API 서버를 구현하는데 목표를 두고 있습니다. 

## :pushpin: 중점사항 
* Spring JPA를 사용한  Relational DB와 Object의 매핑
* Spring Web를 사용한 RESTful API 구현
* Spring Scheduler를 사용한 정산 스케줄러 구현
* Issue, Pull Request, Actions 등 GitHub 제공 기능 적극 활용
* GitHub Actions를 사용한 지속적 통합
* Git Flow, 코딩 컨벤션, 커밋 컨벤션 준수

## :pushpin: 프로젝트 구조도
![image](https://user-images.githubusercontent.com/33937365/183288177-2dabc876-b61d-4788-8a26-3558d4b65ce4.png)

## :pushpin: ERD
![image](https://user-images.githubusercontent.com/33937365/183280542-d596dc1b-1032-422e-9c88-2a27aa3d29b8.png)

## :pushpin: 프로젝트 진행 규칙
### 이슈
* 모든 요구사항은 Issue에 먼저 올린 뒤 작업한다

### Git Flow
* main: 실제 서버에 배포할 브랜치
* develop: 개발 브랜치
* feature/n: 기능 구현 브랜치
  * n에 이슈번호를 넣어 브랜치를 만들어 작업하고 테스트까지 마친 뒤
  * develop으로의 Pull Request를 만들어 팀원에게 리뷰받는다
  * 리뷰가 끝나면 Merge한다

### 커밋 메시지
* 커밋 내용에 맞는 머리말을 사용한다
  * feat: 기능 추가
  * fix: 기능에 문제가 있어 수정
  * refactor: 코드 리팩터링 (동작 변경 전혀X)
  * docs: 문서 수정
  * chore: 빌드 업무 수정, 패키지 매니저 수정, gitignore, 프로젝트 초기설정 등
* 커밋 메시지 제목으로 커밋 내용을 알기 어렵다면 본문으로 추가 설명한다

### 코딩 컨벤션
* [네이버 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)을 지키며 작업한다
* Client-Controller-Service 오가는 객체의 이름은 Protocol로 한다
* Service-Repository-DB 오가는 객체의 이름은 Entity로 한다
* 주석은 정말 꼭 필요한 경우가 아니라면 달지 않는다