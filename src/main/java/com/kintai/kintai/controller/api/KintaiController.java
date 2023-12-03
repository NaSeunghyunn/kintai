package com.kintai.kintai.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KintaiController {

    @GetMapping("/api/kintai")
    public String find() {
        return "OK";
    }
}
