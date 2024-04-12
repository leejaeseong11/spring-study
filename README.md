## Spring Study

### 출처 강의

1. [스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술](https://www.inflearn.com/course/스프링-입문-스프링부트)

- hellospring

2. [스프링 핵심 원리 - 기본편](https://www.inflearn.com/course/스프링-핵심-원리-기본편)

- core

3. [스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술](https://www.inflearn.com/course/스프링-mvc-1)

- servlet
- springmvc
- item-service

4. [스프링 MVC 2편 - 백엔드 웹 개발 활용 기술](https://www.inflearn.com/course/스프링-mvc-2)

- thymeleaft-basic
- form
- message
- validation
- login
- exception
- typeconverter
- upload

5. [스프링 DB 1편 - 데이터 접근 핵심 원리](https://www.inflearn.com/course/스프링-db-1)
- jdbc

6. [스프링 DB 2편 - 데이터 접근 활용 기술](https://www.inflearn.com/course/스프링-db-2)
- itemservice-db
- springtx

7. [스프링 핵심 원리 - 고급편](https://www.inflearn.com/course/스프링-핵심-원리-고급편)
- advanced
- proxy
- aop

8. [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic)
- ex1-hello-jpa
- jpashop

### 스프링 입문

환경 설정

- Java 17, Intellij or VS Code
- Maven vs Gradle
- Dependencies
  - Spring Web
  - Thymeleaf
- 폴더 구조 설명
- 라이브러리
  - spring-boot-starter-web (tomcat)
  - spring-boot-starter-thymeleaf
  - spring-boot-starter (spring core, logging)
  - spring-boot-starter-test

웹 기초

- 정적 컨텐츠: resources > static > index.html
- MVC 개념
- 템플릿 엔진 (Thymeleaf)
  - 컨트롤러에서 리턴한 템플릿을 찾아서 html을 렌더링 (viewResolver)
- spring boot devtools 쓰면 서버 재시작 없이 리로드 가능

터미널에서 빌드 및 실행

- `./gradlew build`
- `cd build` > `cd libs`
- `java -jar hello-spring-0.0.1-SNAPSHOT.war`
- `./gradlew clean` > `./gradlew clean build` → 빌드 다시 함

웹 기초 2

- 템플릿 엔진에 @RequestParam으로 값 받기
- 정적 컨텐츠 vs MVC와 템플릿 엔진 vs API
- 정적 컨텐츠는 컨트롤러가 없으므로, 바로 정적으로 html을 띄움
- 과거 뷰(html)에서만 다 개발하는 것 대신 비즈니스 로직을 분리함 (관심사 분리) → MVC
- API → @ResponseBody
  - 일반적으로 JSON 반환, 과거에는 XML이란 걸 사용했었음
  - JsongConverter, StringConverter

spring mvc

- why → rest 기반의 spa를 쉽게 구현할 수 있게 + front end
- xml을 사용하거나 사용하지 않고 접근할 수 있음
- controller가 model을 주고 받으며 view를 보여줌
- dispatcher servlet - entry point
- view resolver - locates view to serve
- servlet-config
- Plain Old Java Object → Bean

jUnit

- 테스트 → 메인을 매번 실행할 수 없음, 한 번에 모든 테스트를 할 수 없음
- 테스트 순서에 의존적이지 않음 → 각 테스트 데이터 클리어 @AfterEach
- TDD - 테스트 먼저 만들고 구현하기
- 코드가 길어지는데 테스트 코드가 없으면 매우 많은 문제가 발생함

dependency injection

```
memberRepository = new MemoryMemberRepository();
memberService = new MemberService(memberRepository);
```

- memberService에 memberRepository를 직접 생성하지 않고, 생성한 객체를 넘겨줌

스프링 빈, 의존관계

- ex) 멤버 컨트롤러가 멤버 서비스를 의존하고 있음 → 스프링 컨테이너가 처음 만들어질 때 @Controller로 어노테이션 해놓은 걸 관리함
- Controller, Service, Repository 를 사용하는게 정형화된 패턴(@Autowired를 이용해 컨트롤러가 서비스를 사용할 수 있게 의존성 주입을 하는 것이다 = 생성자 위에 어노테이션 쓰면 됨) = 컴포넌트 스캔 / 또는 자바 코드로 직접 스프링 빈 등록해야 함
- 스프링 빈은 싱글톤으로 등록됨
- 직접 등록하려면 SpringConfig 파일 만들어서 @Bean 등록해야 함.
- 생성자 주입, 필드 주입, setter 주입 다양한 방법이 있음. 권장하는 방식은 생성자를 통해서 주입하는 것임

API

- “/”으로 GetMapping 되는 게 있으면 그게 우선순위가 더 높고 없을 때 index.html이 띄워짐
- 데이터를 전송할 때는 PostMapping

DB

- 데이터베이스 설치 (sql)
- sql ddl도 코드에 따로 보관하는 것이 좋다?
- jdbc gradle 과 sql 연결하는 dependencies 추가 , application.properties에 db 경로 추가. id, pw도 추가해야 함
- vscode에서 gradle depenedency 추가 후 리프레시하는 법 알려주기
- 기존에 MemoryMemberRepository 쓰던 걸 JdbcMemberRepository 이렇게 구현체를 바꿔끼기만 하면 쉽게 코드를 바꿔끼울 수 있음 → 스프링의 큰 장점 == 개방-폐쇄 원칙(Solid 중 하나)

spring test

- @SpringBootTest로 스프링 테스트가 가능함
- @Autowired로 의존성 받아서 가져올 수 있움
- @Transactional 이용하면 db에 넣었던 데이터 롤백해줌, 물론 @Commit도 가능
- spring을 올리지 않은 테스트 (단위 테스트) - 빠름, 권장
- spring container까지 올려서 하는 테스트 (통합 테스트) - 느림

JdbcTemplate

- 생성자가 딱 하나면 @Autowired 생략 가능, DataSource는 사용
- jdbc로 굉장히 길지만 jdbctemplate로 쉽게 만들 수 있음 → 템플릿 메서드 패턴을 사용하는 것임
- 개발자 시간의 60%는 테스트 코드를 짤만큼 중요함

JPA

- 객체 중심의 설계로 패러다임을 전환하여 개발 생산성을 크게 높일 수 있음
- gradle - implementation 'org.springframework.boot:spring-boot-starter-jpa’
- application.properties 설정 - spring.jpa.show-sql=true, spring.jpa.hibernate.ddl-auto=none
- EntityManger라는 곳에 설정들을 모아서 가져와줌

스프링 데이터 JPA

- JPA를 편리하게 사용할 수 있도록 해주는 도구임
- 기본적인 CRUD 단순 조회 등 공통적인 것들은 자동으로 만들어 줌
- 공통적인 것 외에는 구현을 해주어야 함
- `Optional<Member> findByName(String name);` 이렇게 구현하면 `select m from Member m where m.name` 이런 쿼리를 자동으로 해주는 것임. 조건 등의 복잡한 쿼리도 findByNameAndId 이런 식으로 만들 수 있음
- 페이징 기능도 자동으로 해 줌

AOP

- 필요한 상황: 모든 메소드의 호출 시간을 측정하고 싶다면? → 모든 메소드에 try, finally로 시간측정 해야하는 반복적인 작업을 해야 함
- 공통 관심 사항(메소드 호출 시간 측정)과 핵심 관심 사항(실제 비지니스 로직)을 분리하는 것이 먼저
- joinPoint, @Around로 적용할 범위 설정 가능
- AOP가 프록시를 만들어서 동작을 하고, joinPoint.proceed()에서 실제 코드가 동작함

### 스프링 핵심 원리

블로그 내용 정리

- https://blog.naver.com/easeon11/223237985559
- https://blog.naver.com/easeon11/223247387412
- https://blog.naver.com/easeon11/223257272458
- https://blog.naver.com/easeon11/223260678783
- https://blog.naver.com/easeon11/223314303667

### HTTP 웹 기본 지식

블로그 내용 정리

- https://blog.naver.com/easeon11/223318091407
- https://blog.naver.com/easeon11/223326691963
- https://blog.naver.com/easeon11/223328450396

### 스프링 MVC

블로그 내용 정리

- https://blog.naver.com/easeon11/223333615748
