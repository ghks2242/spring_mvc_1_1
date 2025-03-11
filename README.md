# spring_mvc_1_1
springmvc1편 45강 새로운 프로젝트로 시작 jsp -> thymelaf

---
프로젝트 생성시 JAR 선택이유 
1. JSP 대신 Thymeleaf 사용
2. JAR 를 사용하면 내장서버 톰캣을 사용하고 webapp 경로도 사용 하지않는다 내장서버 사용에 최적화가 되어있다 최근에는 주로 이방식 사용
3. WAR 를 사용해도 내장서버 사용이 가능하지만 주로 외부 서버에 배포하는 목적으로 사용

---

스프링부트에 JAR 를 사용하면 /resources/static/index.html 위치에 index.html 파일을 두면 Welcome 페이지로 처리해준다 ( 스프링 부트가 지원하는 정적 컨텐츠 위치에 /index.html 이 있으면된다. )

---
### 로깅 라이브러리
스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리( 'spring-boot-starter-logging' ) 가 함께 포함된다.
스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.
* SLF4J - http://www.slf4h.org
* Logback - http://logback.qos.ch
* 스프링부트가 제공하는 로그 기능 - http://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging

로그 라이브러리는 Logback, Log4J, Log4J2 등등 수많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리다.
쉽게 이야기해서 SLF4J는 인터페이스고 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.
실무에서는 스프링 부트가 기본으로 제공하는 Logback을 대부분 사용한다.

#### 로그선언
* private logger log = LoggerFactory.getLogger(getClass())
* private static final Logger log = LoggerFactory.getLogger(xxx.class)
* @Slf4j : 롬복 사용가능

#전체 로그 레벨 설정(기본 info)
logging.level.root=info

#hello.springmvc 패키지와 그 하위 로그 레벨 설정
logging.level.hello.springmvc=debug

### 올바른 log 사용법
만약 logging.level.hello.springmvc=debug 로 세팅이되어있으면 


    String name = "Spring";
    log.trace( "trace my log=" + name );
    -> 로그세팅이 debug 이므로 출력이 되지않는다 하지만 + 때문에 자바에서는 연산이 일어남 ( 메모리와 리소스가 사용됨 )
        1. "trace my log=" + "Spring" 
        2. "trace my log=Spring"
        연산은 다 할대로 다하고 출력은 하지않는다 

    log.trace( "trace my log ", name );
    -> 메서드의 파라미터 형태로 넘기므로 실행이 되지않는다.

### 로그 사용시 장점
* 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼수 있고 출력 모양을 조정할수있다.
* 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게 설정하여 조절할수있다.
* 시스템 아웃 콘솔에만 출력하는것이 아니라, 파일이나 네트워크등, 로그를 별도의 위치에 남길수있다. 특히 파일로 남길때는 일별, 특정 용량에 따라 로그를 분할하는것이 가능하다.
* 성능도 일반 System.out 보다 좋다 (내부 버퍼링, 멀티쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야한다.

---
#### @RestController
* @Controller 는 반환값이 String 이면 뷰 이름으로 인식된다 그래서 뷰를 찾고 뷰가 렌더링 된다
* @RestController 는 반환 값으로 뷰를 찾는것이 아니라 HTTP 메시지 바디에 바로 입력한다 (@Controller + @ResponseBody)


