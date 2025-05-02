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
* SLF4J - http://www.slf4h.org // 인터페이스고 
* Logback - http://logback.qos.ch // 수많은 구현체중 하나이다
* 스프링부트가 제공하는 로그 기능 - http://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging

로그 라이브러리는 Logback, Log4J, Log4J2 등등 수많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리다.
쉽게 이야기해서 **SLF4J는 인터페이스고 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.**
실무에서는 스프링 부트가 기본으로 제공하는 Logback을 대부분 사용한다.

#### 로그레벨에따라 TRACE -> DEBUG -> INFO -> WARN -> ERROR 

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
  * Map(key=value)
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

---
### HTTP 요청 메시지 - 단순 텍스트

* #### HTTP message body 에 데이터를 직접담아서 요청
  * HTTP API 에서 주로 사용 JSON, XML, TEXT
  * 데이터 형식은 주로 JSON 사용
  * POST, PUT, PATCH

요청 파라미터와 다르게 HTTP 메시지 바디를 통해 직접 데이터가 넘어오는 경우 
@RequestParam, @ModelAttribute 사용할수가 없다 ( 물론 HTML Form 형식으로 전달하는 경우는 요청 파라미터로 인정된다)

* 먼저 가장 단순한 텍스트 메시지를 HTTP 바디에 담아서 전송하고 읽어보자
* HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다.


    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
  
        log.info("message body = {}", messageBody);
        response.getWriter().write("ok");
    }

# ---

### 스프링 MVC 는 다음 파라미터를 지원한다
* InputStream(Reader) : HTTP 요청 메시지 바디의 내용을 직접 조회
* OutPutStream(Writer) : HTTP 응답 메시지의 바디에 직접 결과 출력


    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws Exception{
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
  
        log.info("message body = {}", messageBody);
        writer.write("ok");
    }
# ---
### 스프링 MVC 는 다음 파라미터를 지원한다
* HttpEntity : HTTP header, body 정보를 편리하게 조회
  * 메시지 바디 정보를 직접조회
  * 요청 파라미터를 조회하는 기능과 관계없음 @RequestParam X , @ModelAttribute X
* HttpEntity 는 응답에도 사용가능
  * 메시지 바디 정보 직접반환
  * 헤더정보 포함 가능
  * view 조회 X

HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
* RequestEntity
  * HttpMethod, url 정보가 추가, 요청에서 사용
* ResponseEntity
  * HTTP 상태 코드 설정가능, 응답에서 사용
  * return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)


    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws Exception{

        String messageBody = httpEntity.getBody();
        log.info("message body = {}", messageBody);
        return new HttpEntity<>("ok");
    }

### !참고 
스프링 MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데, 이때 HTTP 메시지 컨버터 (HttpMessageConverter) 라는 기능을 사용한다.
이것은 조금뒤에 HTTP 메시지 컨버터에서 설명하겠다.

# ---

### RequestBody 
@RequestBody 를 사용하면 HTTP 메시지 바디정보를 편리하게 조회할수있다. 참고로 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면된다.
이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam , @ModelAttribute 와는 전혀 관계가 없다.

### 요청파라미터 vs HTTP 메시지 바디
* 요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
* HTTP 메시지 바디를 직접조회하는 기능 : @RequestBody

### @ResponseBody
@ResponseBody 를 사용하면 응답결과를 HTTP 메시지 바디에 직접담아 전달할수있다. 물론 이경우에도 view 를 사용하지 않는다


    @PostMapping("/request-body-string-v4")
    @ResponseBody
    public String requestBodyStringV4(@RequestBody String messageBody) throws Exception{

        log.info("message body = {}", messageBody);
        return "OK";
    }

---
### @RequestBody 객체 파라미터
* @RequestBody HelloData helloData
* @RequestBody 에 직접만든 객체를 지정할수있다.

HttpEntity @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON 도 객체로 변환해주는데, 우리가 방금 V2 에서 했던 작업을 대신 처리해준다.

#### @RequestBody 는 생략 불가능
@ModelAttribute 에서 학습한 내용을 떠올려보자
스프링은 @ModelAttribute, @RequestPara 해당 생략시 다음과 같은 규칙을 적용한다.

* String, int, Integer 같은 단순타입 = @RequestParam
* 그외 나머지 = @ModelAttribute ( argument resolver 로 지정해둔 타입 외 )


    따라서 이경우 HelloData 에 @RequestBody 를 생략하면 @ModelAttribute 되어버린다
    HelloData data -> @ModelAttribute HelloData data
    따라서 생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게된다.
####

    @PostMapping("/request-body-json-v3")x
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
        return "ok";
    }

### ResponseBody
응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄수있다.
물론 이 경우에도 HttpEntity 를 사용해도 된다.

* @RequestBody 요청
  * JSON 요청 -> HTTP 메시지 컨버터 -> 객체
* @ResponseBody 응답
  * 객체 -> HTTP 메시지 컨버터 -> JSON 응답

---
HTTP 응답 - 정적 리소스, 뷰 템플릿

* 정적 리소스
  * 예) 웹 브라우저에 정적인 HTML, css, js을 제공할 때는, 정적리소스를 사용한다
* 뷰 템플릿 사용
  * 예) 웹 브라우저에 동적인 HTML 을 제공할 때는 뷰 템플릿을 사용한다. (서버사이드 랜더링)
* HTTP 메시지 사용
  * HTTP API 를 제공 하는 경우에는 HTML 이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로 실어 보낸다.

### 정적 리소스 
스프링 부트는 클래스 패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.

    /static, /public, /reseources, /META-INF/resources 

src/main/resources 는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로이다.
따라서 다음 디렉토리에 리소스를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.

* #### 정적 리소스 경로
    * src/main/resources/static

* #### 다음 경로에 파일이 들어있으면
    * src/main/resources/static/basic/hello-form.html

* #### 웹 브라우저에서 다음과 같이 실행된다
    * http://localhost:8080/basic/hello-form.html

정적 리소스는 해당파일 변경없이 그대로 사용하는것이다.

### 뷰 템플릿
뷰 템플릿을 거쳐서 HTML 이 생성되고, 뷰가 응답을 만들어서 전달한다.
일반적으로 HTML 을 동적으로 생성하는 용도로 사용하지만, 다른것들도 가능하다. 뷰 템플릿이 만들수 있는 것이라면 뭐든지 가능하다.

스프링 부트는 기본 뷰 템플릿 경로를 제공한다

* #### 뷰 템플릿 경로
  * src/main/resources/templates
* #### 뷰 템플릿 생성
  * src/main/resources/templates/response/hello.html


    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mv = new ModelAndView("response/hello")
                .addObject("data", "hello");
        return mv;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello2");
        return "response/hello";
    }

    //    권장하지않음
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello3");
    }

#### String 을 반환하는 경우 - View or HTTP 메시지
@ResponseBody 가 없으면 response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고 렌더링한다. <br>
@ResponseBody 있으면 뷰 리졸버를 실행하지안하고 HTTP 메시지 바디에 직접 response/hello 라는 문자가 입력된다

뷰의 논리 이름인 response/hello 를 반환하면 다음경로의 뷰 템플릿이 렌더링된다
templates/response/hello.html

#### Void 를 반환하는 경우
* @Controller 를 사용하고 HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL 을 참고해서 논리 뷰 이름으로 사용
  * 요청 URL: /response/hello
  * 실행: templates/response/hello.html
* 참고로 이방식은 명시성이 너무 떨어지고 이렇게 딱맞는 경우도 많이없어서, 권장하지않는다.

### HTTP 메시지
@ResponseBody, HttpEntity 를 사용하면 뷰 템플릿을 사용하는것이 아니라 HTTP 메시지 바디에 직접 응답 데이터를 출력할수있다.


# HTTP 메시지 컨버터

* 기본문자처리 : StringHttpMessageConverter
* 기본객체처리 : MappingJackson2HttpMessageConverter
* byte 처리 등등 기타 : HttpMessageConverter 가 기본으로 등록되어있다.  

### 스프링 MVC 는 다음의 경우에 HTTP 메시지 컨버터를 사용한다.
* HTTP 요청: @RequestBody, HttpEntity(RequestEntity)
* HTTP 응답: @ResponseBody, HttpEntity(ResponseEntity)

### HttpMessageConverter 인터페이스
* HTTP 메시지 컨버터는 HTTP 요청, 응답 둘다 사용한다
* canRead(), canWrite() : 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크
* read(), write() 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능

### 스프링 부트 기본 메시지 컨버터 (일부생략)

    0 = ByteArrayHttpMessageConverter
    1 = StringHttpMessageConverter
    2 = MappingJackson2HttpMessageConverter

스프링 부트는 다양한 메시지 컨버터를 제공하는데 대상 클래스 타입과 미디어 타입 둘으 체크해서 사용여부를 결정한다
만약 만족하지않으면 다음 메시지 컨버터로 우선순위가 넘어간다.

몇가지 주요 컨버터를 알아보자
* ByteArrayHttpMessageConverter : byte[] 데이터를 처리한다
  * 클래스 타입 : 'byte[]', 미디어타입 '*/*'
  * 요청 예) : @RequestBody byte[] data
  * 응답 예) : @ResponseBody return byte[] 쓰기 미디어타입 'application/octet-stream'
* StringHttpMessageConverter : String 문자로 데이터를 처리한다
  * 클래스 타입 : 'String', 미디어타입 '*/*'
  * 요청 예) : @RequestBody String data
  * 응답 예) : @ResponseBody return "OK" 쓰기 미디어타입 'text/plain'
* MappingJackson2HttpMessageConverter : application/json
  * 클래스 타입 : 객체 또는 'HashMap', 미디어타입 'application/json'
  * 요청 예) : @RequestBody HelloData helloData
  * 응답 예) : @ResponseBody return helloData 쓰기 미디어타입 'application/json'
    

### HTTP 요청데이터 읽기
* HTTP 요청이 오고 컨트롤러에서 @RequestBody, HttpEntity 파라미터를 사용한다
* 메시지 컨버터가 메시지를 읽을수있는지 확인하기 위해 canRead() 호출한다
  * 대상클래스 타입을 지원하는가
    * 예) @RequestBody 의 대상 클래스 ( byte[], String, HelloData )
  * HTTP 요청의 Content-Type 미디어 타입을 지원하는가
    * 예) text/plain, application/json, */*
* canRead() 조건을 만족하면 read() 호출해서 객체 생성하고 반환한다.

### HTTP 응답데이터 생성
* 컨트롤러에서 @ResponseBody, HttpEntity 로 값이 반환된다.
* 메시지 컨버터가 쓸수있는지 확인하기 위해 canWrite() 호출한다
  * 대상클래스 타입을 지원하는가
    * 예) return 의 대상 클래스 ( byte[], String, HelloData )
  * HTTP 요청의 Accept 미디어 타입을 지원하는가 ( 더 정확히 @RequestMapping 의 produces 을 세팅하면 해당값으로 이용 세팅하지않으면 Accept 사용)
    * 예) text/plain, application/json, */*
* canWrite() 조건을 만족하면 write() 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다

예시)
### SpringHttpMessageConverter
    content-type : application/json
    @RequestMapping
    void hello(@ReqeustBody String data) {}

### MappingJackson2HttpMessageConverter
    content-type : application/json
    @RequestMapping
    void hello(@ReqeustBody HelloData data) {}

### ? (메시지컨버터 작동안됨)
    content-type : text/html
    @RequestMapping
    void hello(@ReqeustBody HelloData data) {}

### 도대체 어디서 동작하는걸까?
모든비밀은 어노테이션 기반 컨트롤러 -> @RequestMapping 을 처리하는 핸들러 어댑터인 RequestMappingHandlerAdapter (요청매핑핸들러) 에 있다 


### ArgumentResolver
생각해보면 어노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할수 있었다.
HttpServletRequest, Model, @RequestParam, @ModelAttribute 같은 어노테이션 
그리고 @RequestBody, HttpEntity, 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다.
이렇게 파라미터를 유연하게 처리할수있는 이유가 바로 ArgumentResolver 덕분이다

어노테이션기반 컨트롤러를 처리하는 RequestMappingHandlerAdapter 는 바로 이 ArgumentResolver 를 호출해서 
컨트롤러가 필요로하는 다양한 파라미터값을 생성한다 그리고 이렇게 파라미터 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.

### HTTP 메시지 컨버터는 어디쯤있을까?
요청시 : ArgumentResolver 호출
HTTP 메시지 컨버터를 사용하는 @RequestBody 도 컨트롤러가 필요로하는 파라미터의값에 사용된다
응답시 : ReturnValueHandler
@ResponseBody 의 경우도 컨트롤러의 반환값을 이용한다.

    요청의 경우 @RequestBody 를 처리하는 ArgumentResolver 가 있고 
    HttpEntity 를 처리하는 ArgumentResolver 있다
    이 ArgumentResolver 들이 HTTP 메시지 컨버터를 사용해서 필요한 객체를 생성하는 것이다
####
    응답의 경우 @ResponseBody 와 HttpEntity 를 처리하는 ReturnValueHandler 가 있다
    여기에서 HTTP 메시지 컨버터를 호출해서 응답결과를 만든다

스프링 MVC 는 @RequestBody, @ResponseBody 가 있으면 RequestResponseBodyMethodProcessor (ArgumentResolver) 
HttpEntity 가 있으면 HttpEntityMethodProcessor (ArgumentResolver) 를 사용한다.


스프링은 다음읆 모두 인터페이스로 제공하고있어 필요하면 언제든지 확장이가능하다
* HandlerMethodArgumentResolver
* HandlerMethodReturnValueHandler
* HttpMessageConverter

기능확장을 위해서는 WebMvcConfigurer 을 상속받아서 스프링 빈으로 등록하면된다
    
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addArgumentResolveres(List<HandlerMethodArgumentResolver> resolveres) {
        ...
      }

      @Override
      public void extendMessageConverters(List<HttpMessageConvereter<?>> converters) {
        ...
      }
    }



  