package com.kintai.kintai.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.*;

class ProfileControllerUnitTest {

    private final String REAL1 = "real1";
    private final String REAL2 = "real2";
    private MockEnvironment env;
    private ProfileController controller;

    @BeforeEach
    void beforeEach() {
        env = new MockEnvironment();
        controller = new ProfileController(env);
    }

    @Test
    @DisplayName("active_Profileが存在しないと「rea1」がデフォルト")
    void no_active_profile() {
        // given
        String expectedProfile = REAL1;

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real1が照会する")
    void real1_profile() {
        // given
        String expectedProfile = REAL1;
        env.addActiveProfile(expectedProfile);

        // when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real2が照会する")
    void real2_profile() {
        // given
        String expectedProfile = REAL2;
        env.addActiveProfile(expectedProfile);

        // when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real_profileが存在しないと「real1」がデフォルト")
    void no_real_profile() {
        // given
        String expectedProfile = REAL1;
        env.addActiveProfile("something");

        // when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}