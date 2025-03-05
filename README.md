# spring_mvc_1_1
springmvc1편 45강 새로운 프로젝트로 시작 jsp -> thymelaf

---
프로젝트 생성시 JAR 선택이유 
1. JSP 대신 Thymeleaf 사용
2. JAR 를 사용하면 내장서버 톰캣을 사용하고 webapp 경로도 사용 하지않는다 내장서버 사용에 최적화가 되어있다 최근에는 주로 이방식 사용
3. WAR 를 사용해도 내장서버 사용이 가능하지만 주로 외부 서버에 배포하는 목적으로 사용

---

스프링부트에 JAR 를 사용하면 /resources/static/index.html 위치에 index.html 파일을 두면 Welcome 페이지로 처리해준다 ( 스프링 부트가 지원하는 정적 컨텐츠 위치에 /index.html 이 있으면된다. )
