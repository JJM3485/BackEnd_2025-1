package com.example.bcsd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IntroduceController {

    //실습 1번
    //@GetMapping("/introduce")
    @GetMapping(value = "/introduce", params = "!name")
    public String introduce() {
        return "introduce";
    }

    //실습 2번
    @GetMapping(value = "/introduce", params = "name")
    @ResponseBody
    public String introduceGetName(@RequestParam("name") String name) {
        return "안녕하세요 제 이름은 " + name + "입니다!";
    }
}