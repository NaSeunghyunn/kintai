package com.kintai.kintai.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.*;

class ProfileControllerUnitTest {

    private final String RESULT_REAL1 = "real1";
    private final String RESULT_REAL2 = "real2";
    private final String RESULT_DEFAULT = "default";
    private final String RESULT_SOMETHING = "something";
    private MockEnvironment env;
    private ProfileController controller;

    @BeforeEach
    void beforeEach() {
        env = new MockEnvironment();
        controller = new ProfileController(env);
    }

    @Test
    @DisplayName("active_Profileが存在しないと「default」が出力される")
    void no_active_profile() {
        // given
        String expectedProfile = RESULT_DEFAULT;

        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real1が照会する")
    void real1_profile() {
        // given
        String expectedProfile = RESULT_REAL1;
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
        String expectedProfile = RESULT_REAL2;
        env.addActiveProfile(expectedProfile);

        // when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("real_profileが存在しないと最初目のprofileが出力される")
    void no_real_profile() {
        // given
        String expectedProfile = RESULT_SOMETHING;
        env.addActiveProfile(RESULT_SOMETHING);

        // when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}