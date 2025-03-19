package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String,String> headerMap, // MultiValueMap 은 하나의 키에 여러가지 값을 받을수있다.
                          @RequestHeader("host") String host, // 단일 헤더 받는방법 host 는 필수헤더이다
                          @CookieValue(value = "myCookie", required = false) String cookie // required 는 없어도 된다는뜻

    ) {

        log.info("request = {}" , request );
        log.info("response = {}" , response );
        log.info("httpMethod = {}" , httpMethod );
        log.info("locale = {}" , locale );
        log.info("headerMap = {}" , headerMap );
        log.info("host = {}" , host );
        log.info("cookie = {}" , cookie );

        return "OK";
    }
}
