package com.softserve.ita.java442.cityDonut.security.google;

import org.springframework.social.google.api.impl.AbstractGoogleApiOperations;
import org.springframework.web.client.RestTemplate;

public class GoogleOauth2Template extends AbstractGoogleApiOperations implements OAuth2Operations {

    private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=";

    protected GoogleOauth2Template(RestTemplate restTemplate, boolean isAuthorized) {
        super(restTemplate, isAuthorized);
    }

    @Override
    public UserInfo getUserInfo(String access_token) {
        return getEntity(USER_INFO_URL + access_token, UserInfo.class);
    }
}
