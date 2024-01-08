package com.kintai.kintai.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@ActiveProfiles("real2")
class ProfileControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("ログイン認証なしでprofileを取得")
    void callWhenNonLogin() throws Exception {
        // given
        String url = "/profile";

        // when, then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("real2"))
                .andDo(print());
    }
}