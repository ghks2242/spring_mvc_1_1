package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     *
     * @return
     */
//    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    @GetMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "OK";
    }


    /**
     * @PathVariable("userId") String userId -> @PathVariable String userId ( 변수명이 같으면 축약가능)
     * @param data
     * @return
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath {}", data);
        return "OK";
    }

    /**
     * PathVariable 다중사용
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId = {} orderId = {}", userId, orderId);
        return "OK";
    }


    /**
     * 파라미터로 추가 매핑 ( 특정 파라미터가 있어야 매핑가능)
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params={"mode=debug", "data=good"}
     * @return
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "OK";
    }

    /**
     * 특정 헤더로 추가 매핑 ( 특정 헤더가 있어야 매핑가능)
     * headers="mode"
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug"
     * @return
     */
    @GetMapping(value ="/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "OK";
    }

    /**
     * header 정보를 받으려면 @RequestHeader 사용하면됨
     * @param headers
     * @return
     */
    @GetMapping(value = "/mapping-header2", headers = "mode=debug")
    public String mappingHeader(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> log.info("Header: {} = {}", key, value));
        return "OK";
    }

    /**
     * Content-type 헤더 기반 추가 매핑 Media Type
     * consumes 소비하는것 -> 요청이들어오고 컨트롤러가 소비하는것
     * consumes = "application/json"
     * consumes = "!application/json"
     * consumes = "application/*"
     * consumes = "*\/*"
     * MediaType=APPLICATION_JSON_VALUE
     * @return
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "OK";
    }


    /**
     * Accept 헤더 기반 Media Type
     * produces 생산 -> 컨트롤러가 생산해내는 타입
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     * @return
     */
    @PostMapping(value = "/mapping-produces", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "OK";
    }
}
