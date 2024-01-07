package com.kintai.kintai.auth;

import lombok.Getter;

@Getter
public class LoginMember {
    private Long id;
    private String name;

    public LoginMember(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
