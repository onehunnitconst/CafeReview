# CafeReview
카페 평점 서비스로 각 브랜드 별 카페의 음료에 대한 평가를 할 수 있고 본인만의 레시피를 공유할 수 있는 서비스

Figma URL : https://www.figma.com/proto/Z1Fe0ggquhnUQlIVDV0NQq/%EC%84%B8%EB%AA%A8%EB%84%A4%EB%AA%A8?node-id=2%3A58&scaling=min-zoom


# 브랜치 관리 전략
Git Flow를 사용하여 브랜치를 관리합니다.

![image](https://user-images.githubusercontent.com/53288312/111865064-1da81d80-89a8-11eb-954b-d45edc119421.png)

Main : 배포시 사용합니다. 아직 배포단계에 이르지 않아 Main 브랜치에 내용이 없습니다.
Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
Feature : 기능 개발을 진행할 때 사용합니다.
Release : 배포를 준비할 때 사용합니다.
Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.

# 프로젝트 사용 기술
- Spring Boot
- gradle
- typescript
- mysql
