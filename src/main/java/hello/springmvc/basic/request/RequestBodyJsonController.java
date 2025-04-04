package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * {"username" : "hello", "age" : 20}
 * content-type : application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={} " , messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");
    }


    @PostMapping("/request-body-json-v2")
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={} " , messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @PostMapping("/request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    // 역시나 HttpEntity 로도 사용가능
    @PostMapping("/request-body-json-v4")
    @ResponseBody
    public String requestBodyJsonV4(HttpEntity<HelloData> data) throws IOException {
        HelloData helloData = data.getBody();
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
        return "ok";
    }


    /**
     * @RequestBody 생략 불가능 (@ModelAttribute 가 적용되어버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter ( content-type : application/json )
     *
     * @ResponseBody
     * 메시지 바디 정보 직접 변환 ( view 조회 X )
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter ( Accept : application/json ) // Accept 클라이언트에 반환될 타입
     * 클라이언트에 반환될 타입을 보고 어떤 메시지 컨버터를 사용해야할지 정한다
     */
    // HTTP 메시지 컨버터가 들어올때도 적용되지만 나갈때도 적용된다.
    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={} " , helloData.getUsername(), helloData.getAge());
//        return (HashMap<String, Object>) new HashMap<String,Object>(){{
//            put("username", "hello");
//            put("age", 20);
//        }};
        return helloData;
    }
}
