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
* @RequestMapping("hello-basic") URL 호출이 오면 이메서드가 실행되도록 매핑한다, 대부분의 속성을 배열로 제공하므로 다중 설정이 가능 {"hello-basic" , "hello-go"} 

#### 둘다허용
다음 두가지는 요청이 다른 URL 이지만 스프링은 다음 URL 요청들을 같은 요청으로 매핑한다.
* 매핑: "hello-basic"
* URL 요청 : "/hello-basic", "/hello-basic/"

/ 한것도 같이 매핑해준다

### HTTP 메서드
@RequestMapping 에 method 속성을 지정하지 않으면 HTTP 메서드와 무관하게 호출된다 즉 GET,HEAD,POST,PUT,PATCH,DELETE 모두 허용

#### @PathVariable 사용
* PathVariable 이름과 파라미터 이름이 같으면 생략할수있다


    /**
     * PathVariable
     * @PathVariable("userId") String userId -> @PathVariable String userId ( 변수명이 같으면 축약가능)
     *
     * */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath {}", data);
        return "OK";
    }

다른여러매핑은 MappingController 에서 확인
* 파라미터타입 추가매핑
* 헤더타입 추가매핑
* Content-type 헤더 기반추가매핑
* Accept 헤더 기반 
---
### 참고
@Controller 의 사용 가능한 파라미터 목록은 다음공식 메뉴얼에서 확인할 수 있다.
https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-arguments

### 참고
@Controller 의 사용 가능한 응답 값 목록은 다음공식 메뉴얼에서 확인할 수 있다.
https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-return-types
---
### 클라이언트에서 서버로 요청데이터를 전달할때는 3가지 방식을 사용한다
1. GET - 쿼리 파라미터
   * /url?username=hello&age=20
   * 메시지 바디 없이, URL 의 쿼리 파라미터에 데이터를 포함해서 전달
   * 예) 검색, 필터, 페이징 등에서 많이사용
2. POST - HTML Form
   * content-type: application/x-www-form-urlencoded
   * 메시지 바디에 쿼리파라미터 형식으로 전달 username=hello&age=20
   * 예) 회원가입, 상품주문, HTML Form 사용
3. HTTP message body
   * HTTP API 에서 주로 사용, JSON, XML, TEXT
   * 데이터 형식은 주로 JSON 사용
   * POST, PUT, PATCH


### 요청 파라미터 - 쿼리파라미터, HTML Form
HttpServletRequest 의 request.getParameter() 을 사용하면 다음 두가지 요청 파라미터를 조회할수있다.
* GET - 쿼리 파라미터
* POST - HTML Form

-> GET 쿼리파라미터 전송방식이든, POST HTML Form 전송 방식이든 둘다 형식이 같으므로 구분없이 조회할수있다.
RequestParamController 에 requestParamV1 메소드를 실행시키나(GET) http://localhost:8080/basic/hello-form.html (POST) 에서 데이터를 입력하나 같은 컨트롤러에 메서드가 동작한다.

* @RequestParam.required
  * 파라미터 필수여부
  * 기본값이 파라미터 필수(true) 이다
* /request-param-required 요청
  * username 이 없으므로 400 예외가 발생

### 주의! 파라미터 이름만 사용
/request-param-required?username=
으로 요청시 username 을 빈문자로보고 통과시킨다 null과 "" 은 엄연히 다르다

### 주의!! 기본형(primitive)에 null 입력
* /request-param-required 요청
* @RequestParam(required = false) int age


    @RequestParam(required = false) int age 는 원래라면 age 파라미터가 없어도 정상적으로 동작하여야한다
    근데 실제로 해보면 작동하지않는다 그이유는 int 는 기본형이라 null 이 될수 없기때문이다 (자바에서 기본형은 0이라는 값이 들어간다)
    저대로 사용하고 싶으면 int 를 Integer 로 변경해야한다
    자바에서 Integer 객체형이라 null 이 가능하다
    => 따라서 null 을 받을수있는ㄷ Integer 로 변경하거나 defaultValue 를 사용해야한다

defaultValue 는 빈문자의 경우에도 설정이 적용된다


#### 파라미터를 맵으로조회
* @RequestParam Map
  * Mao(key=value)
* @RequestParam MultiValueMap
  * MultiValueMap(userIds = [id1, id2, ...])
?userIds=id1&userIds=id2

파라미터 값이 1개면 Map 을 사용해도되지만 그렇지 않다면 MultiValueMap 을 사용하자

---
## HelloData
롬복에 @Data 를 사용하면 @Getter, @Setter, @ToString, @EqualAndHashCode, @RequiredArgsConstructor 를 자동으로 적용해준다
    
    HelloData 에 데이터를 넣고 log.info("HelloData = {}, helloData) 을 해도 객체가 찍히는게 아니라 데이터가 잘보인다 
    그이유는 롬복에 @Data 에 있는 @ToString 이 자동으로 만들어준다

## @ModelAttribute

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@RequestParam String username, @RequestParam Integer age) {
       HelloData helloData = new HelloData();
       helloData.setUsername(username);
       helloData.setAge(age);
       
       log.info("username = {}, age = {} ", helloData.getUsername(), helloData.getAge());
       log.info("HelloData = {} " , helloData);
       return "OK";
    }
이코드를 @ModelAttribute 를 사용하면

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {} ", helloData.getUsername(), helloData.getAge());
        log.info("HelloData = {} " , helloData);
        return "OK";
    }

마치 마법처럼 HelloData 객체가 생성되고 요청파라미터 값이 모두 들어가 있다.
스프링 MVC 는 @ModelAttribute 가 있으면 다음을 실행한다.

* HelloData 객체를 생성한다
* 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다 그리고 해당 프로퍼티의 setter 를 호출해서 파라미터의 입력(바인딩) 한다
* 예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다

### 프로퍼티
객체에 getUsername(), setUsername(), 메서드가 있으면 이객체는 username 이라는 프로퍼티를 가지고있다.
username 프로퍼티의 값을 변경하면 setUsername() 이 호출되고, 조작하면 getUsername() 이 호출된다

<br>

### 바인딩 오류
"age=abc" 처럼 숫자가 들어가야할곳에 문자를 넣으면 BindException 이 발생한다 이런 바인딩 오류 처리하는 방법은 검증부분에서 다룬다.

<br>

### @ModelAttribute 도 생략이 가능하다
그런데 @RequestParam 도 생략할수 있으니 혼란이 발생할수있다.
스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
* String, int, Integer 같은 단순타입 => @RequestPara
* 나머지 @ModelAttribute (argument resolver 로 지정해둔 타입은 예외)


