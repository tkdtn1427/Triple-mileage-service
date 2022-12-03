# 📌 Triple-milliage-service
- 사용자들의 여행 장소에 리뷰를 작성,수정,삭제할 때, 전체/개인에 대한 포인트 부여 히스토리를 남기고
개인별 누적 포인트를 관리하고자 하는 서비스
- 사용자는 장소별 1개의 리뷰만을 남길 수

## 📌 SPECIFICATIONS
- 리뷰 작성이 이뤄질때마다 리뷰 작성 이벤트가 발생하고, 아래 API로 이벤트를 전달합니다.

![image](https://user-images.githubusercontent.com/81614803/205430221-9bbcd308-34cb-4f60-bcbd-6cedb70fbfa8.png)

- type: 미리 정의된 string 값을 가지고 있습니다. 리뷰 이벤트의 경우 "REVIEW" 로 옵니다.
- action: 리뷰 생성 이벤트의 경우 "ADD" , 수정 이벤트는 "MOD" , 삭제 이벤트는 "DELETE" 값
을 가지고 있습니다.
- reviewId: UUID 포맷의 review id입니다. 어떤 리뷰에 대한 이벤트인지 가리키는 값입니다.
- content: 리뷰의 내용입니다.
- attachedPhotoIds: 리뷰에 첨부된 이미지들의 id 배열입니다.
- userId: 리뷰의 작성자 id입니다.
- placeId: 리뷰가 작성된 장소의 id입니다.

한 사용자는 장소마다 리뷰를 1개만 작성할 수 있고, 리뷰는 수정 또는 삭제할 수 있습니다. 리뷰 작성 보상 점수는
아래와 같습니다.
- 내용 점수
  - 1자 이상 텍스트 작성: 1점
  - 1장 이상 사진 첨부: 1점
- 보너스 점수
  - 특정 장소에 첫 리뷰 작성: 1점
  
</br>

## 📌 실행방법

### 1. H2 인메모리 DB이용
```java
(1). build.gradle 에서 runtimeOnly 'com.h2database:h2' 활성화 및 아래 mysql 비활성화
```
![image](https://user-images.githubusercontent.com/81614803/205430717-5b1882c9-14c3-43a4-a5b7-f42a2315a322.png)
```java
(2). ./gradlew build 로 프로젝트 빌드
(3). java -jar build/libs/milliage-0.0.1-SNAPSHOT.jar --spring.profiles.active=local 실행
```

</br>

---

### 2. Mysql 사용
```java
(1). build.gradle 에서 runtimeOnly 'mysql:mysql-connector-java' 활성화 및 위 h2 비활성화
```
![image](https://user-images.githubusercontent.com/81614803/205430820-7b3392a0-500b-4224-9077-5dc7794fc705.png)
```java
(2). mysql에서 사용할 db를 생성하고 생성 후,
(3). application.yml 파일에서 생성한 데이터베이스 아래사진의 1번에, mysql 유저이름을 2번에, 비밀번호를 3번에 입력
```
![image](https://user-images.githubusercontent.com/81614803/205430988-3b1f5979-71f0-4330-9472-07830be99c5c.png)
```java
(4). ./gradlew build 로 프로젝트 빌드
(5). java -jar build/libs/milliage-0.0.1-SNAPSHOT.jar
```

</br>

## 📌 DB ERD & 구현 Schema

![image](https://user-images.githubusercontent.com/81614803/205431555-0cbb47ee-e92b-4ebb-ad3f-8f88328d31cf.png)


</br>

## 📌 테스트 코드
- Mockmvc, BDDMockito, JUnit을 통한 계층별 단위테스트

![image](https://user-images.githubusercontent.com/81614803/205431231-3a36867a-4d5f-46c5-88ca-3b6dd43e9021.png)

  
</br>

## 📌 REQUIREMENTS

- 이 서비스를 위한 SQL(MySQL >= 5.7) 스키마를 설계해 주세요. 테이블과 인덱스에 대한 DDL이 필
요합니다.
- 아래 API를 제공하는 서버 애플리케이션을 작성해 주세요.
  - POST /events 로 호출하는 포인트 적립 API
  - 포인트 조회 API
- 상세 요구사항은 아래와 같습니다.
  - REST API를 제공하는 서버 애플리케이션을 구현해 주세요.
  - 프로그래밍 언어는 Java, Kotlin, Python, JavaScript(혹은 TypeScript) 중에서 선택해
    주세요.
  - Framework, Library는 자유롭게 사용할 수 있어요. 추가의 Data Storage가 필요하다면
  - 여러 종류를 사용해도 돼요.
  - 필수는 아니지만 테스트 케이스를 작성하면 더욱 좋아요!
  - 애플리케이션 실행 방법을 README 파일에 작성해 주세요.
  - 작업한 결과물을 GitHub에 올리고 URL을 알려주세요.
  
</br>

## 📌 REMARKS
- 포인트 증감이 있을 때마다 이력이 남아야 합니다.
- 사용자마다 현재 시점의 포인트 총점을 조회하거나 계산할 수 있어야 합니다.
- 포인트 부여 API 구현에 필요한 SQL 수행 시, 전체 테이블 스캔이 일어나지 않는 인덱스가 필요합니다.
- 리뷰를 작성했다가 삭제하면 해당 리뷰로 부여한 내용 점수와 보너스 점수는 회수합니다.
- 리뷰를 수정하면 수정한 내용에 맞는 내용 점수를 계산하여 점수를 부여하거나 회수합니다.
  - 글만 작성한 리뷰에 사진을 추가하면 1점을 부여합니다.
  - 글과 사진이 있는 리뷰에서 사진을 모두 삭제하면 1점을 회수합니다.
- 사용자 입장에서 본 '첫 리뷰'일 때 보너스 점수를 부여합니다.
  - 어떤 장소에 사용자 A가 리뷰를 남겼다가 삭제하고, 삭제된 이후 사용자 B가 리뷰를 남기면 사
용자 B에게 보너스 점수를 부여합니다.
  - 어떤 장소에 사용자 A가 리뷰를 남겼다가 삭제하는데, 삭제되기 이전 사용자 B가 리뷰를 남기
면 사용자 B에게 보너스 점수를 부여하지 않습니다.
