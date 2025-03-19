package hello.springmvc.basic.requestmapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
@Log4j2
public class MappingClassController {

    /**
     * 회원 목록 조회 : GET    '/users'
     * 회원 등록     : POST   '/users'
     * 회원 조회     : GET    '/users/{userId}'
     * 회원 수정     : PATCH  '/users/{userId}'
     * 회원 삭제     : DELETE '/users/{userId}'
     *
     */

    @GetMapping
    public String users() {
        log.info("get user");
        return "get users";
    }

    @PostMapping
    public String addUser() {
        log.info("post user");
        return "post users";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        log.info("get user {}" ,userId);
        return "get user = " + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        log.info("patch user {}" ,userId);
        return "patch users = " + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        log.info("delete user {}" ,userId);
        return "delete users = " + userId;
    }
}
