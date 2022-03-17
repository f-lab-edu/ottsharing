# OTT 계정 공유 중개 플랫폼
## 3줄 요약
* 파티장은 자신의 OTT 계정을 공유하고 돈을 받습니다
* 파티원은 돈을 지불하고 OTT 계정을 공유받습니다
* 파티장과 파티원은 자동으로 매칭됩니다

## 프로젝트 진행 규칙
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

## 기타 참고사항
* DB_ID, DB_PASSWORD 환경변수 또는 프로그램 인수 설정해야 프로젝트 정상 실행됨
* Swagger 페이지 링크: localhost:8080/swagger-ui/index.html