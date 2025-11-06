package com.example.bcsd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    @GetMapping("/json")
    public JsonResponse getJson() {
        return new JsonResponse(24, "조재민");
    }

    public record JsonResponse(int age, String name) {
    }

}