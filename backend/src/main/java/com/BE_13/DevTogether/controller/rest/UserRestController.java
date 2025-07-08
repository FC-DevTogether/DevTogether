package com.BE_13.DevTogether.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Document", description = "사용자 관련 api")
@RequestMapping("/user")
@RestController
public class UserRestController {

    @Tag(name = "유저 test", description = "test api입니다.")
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
