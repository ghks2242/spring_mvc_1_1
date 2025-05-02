package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    /**
     * 서블릿을 직접다룰떄 처럼
     * HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }

    /**
     * ResponseEntity 엔티티는 HttpEntity 를 상속 받았는데
     * HttpEntity 는 HTTP 메시지의 헤더, 바디정보를 가지고 있다. ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할수있다.
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * @ResponseBody 를 사용하면 view 를 사용하지 않고 HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수있다.
     * ResponseEntity 도 동일한 방식으로 동작한다
     */
    @GetMapping("/response-body-string-v3")
    @ResponseBody
    public String responseBodyV3() {
        return "OK";
    }

    /**
     * ResponseEntity 를 반환한다.
     * HTTP 메시지 컨버터를 통해서 JSON 형식으로 반환된다
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * ResponseEntity 는 HTTP 응답 코드를 설정할수있는데 @ResponseBody 를 사용하면 이런것을 설정하기가 까다롭다.
     * @ResponseStatus(HttpStatus.OK) 어노테이션을 사용하면 응답코드도 설정할수있다
     * !! 물론 어노테이션이기 때문에 응답코드를 동적으로 변경할수는 없다 프로그램 조건에 따라 변경하려면 ResponseEntity 를 사용해야한다.
     */
    @GetMapping("/response-body-json-v2")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
