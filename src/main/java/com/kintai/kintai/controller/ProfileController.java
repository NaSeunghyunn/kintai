package com.kintai.kintai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        return activeProfiles.stream()
                .filter(profile -> profile.equals("real2"))
                .findAny()
                .orElse("real1");
    }
}
