package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hello.springmvc.basic.HelloData;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}, age = {} ", username, age);

        response.getWriter().write("OK");
    }

    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
        log.info("username = {}, age = {} ", memberName, memberAge);
        return "OK";
    }

    @RequestMapping("/request-param-v3")
    @ResponseBody
    // @RequestParam("username") String username -> HTTP 파라미터이름과 변수이름이 같으면 축약가능 -> @RequestParam String username
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username = {}, age = {} ", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-v4")
    @ResponseBody
    // String, int, Integer 등 단순타입이면 @RequestParam 마저도 생략가능
    // 가급적이면 @RequestParam 을 사용해서 명시하는것도 나쁘지않다
    public String requestParamV4(String username, int age) {
        log.info("username = {}, age = {} ", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {

        // @RequestParam(required = false) int age 는 원래라면 age 파라미터가 없어도 정상적으로 동작하여야한다
        // 근데 실제로 해보면 작동하지않는다 그이유는 int 는 기본형이라 null 이 될수 없기때문이다 (자바에서 기본형은 0이라는 값이 들어간다)
        // 저대로 사용하고 싶으면 int 를 Integer 로 변경해야한다
        // 자바에서 Integer 객체형이라 null 이 가능하다
        // => 따라서 null 을 받을수있는ㄷ Integer 로 변경하거나 defaultValue 를 사용해야한다

        log.info("username = {}, age = {} ", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault( @RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) {
        // defaultValue 는 빈문자의 경우에도 설정이 적용된다
        log.info("username = {}, age = {} ", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap( @RequestParam Map<String,Object> paramMap) {
        log.info("username = {}, age = {} ", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }


//    @RequestMapping("/model-attribute-v1")
//    @ResponseBody
//    public String modelAttributeV1(@RequestParam String username, @RequestParam Integer age) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//
//        log.info("username = {}, age = {} ", helloData.getUsername(), helloData.getAge());
//        log.info("HelloData = {} " , helloData);
//        return "OK";
//    }

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {} ", helloData.getUsername(), helloData.getAge());
        log.info("HelloData = {} " , helloData);
        return "OK";
    }


    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    // @ModelAttribute 도 생략할수있다.
    public String modelAttributeV2(HelloData helloData) {
        log.info("username = {}, age = {} ", helloData.getUsername(), helloData.getAge());
        log.info("HelloData = {} " , helloData);
        return "OK";
    }
}

