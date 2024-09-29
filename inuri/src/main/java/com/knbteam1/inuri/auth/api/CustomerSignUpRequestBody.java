package com.knbteam1.inuri.auth.api;

public record CustomerSignUpRequestBody(
        String username,
        String password,
        String name,
        String addr,
        String postcode,
        String tel
) {
}
