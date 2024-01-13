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
    private final List<String> realProfiles = Arrays.asList("real1", "real2");

    @GetMapping("/profile")
    public String profile() {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        String defaultProfile = activeProfiles.isEmpty() ? "default" : activeProfiles.get(0);
        return activeProfiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
