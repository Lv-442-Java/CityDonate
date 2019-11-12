package com.softserve.ita.java442.cityDonut.security.google;

public interface SocialService<Type> {
    String getLogin();
    String getAccessToken(String code);
    Type getUserProfile(String accessToken);
}
