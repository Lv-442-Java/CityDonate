package com.softserve.ita.java442.cityDonut.security.facebook;

import com.softserve.ita.java442.cityDonut.security.google.SocialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookServiceImpl implements SocialService<User> {
    @Value("${spring.social.facebook.app-id}")
    private String facebookId;
    @Value("${spring.social.facebook.app-secret}")
    private String facebookSecret;

    private FacebookConnectionFactory createFacebookConnection(){
        return new FacebookConnectionFactory(facebookId,facebookSecret);
    }
    @Override
    public String getLogin() {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri("http://localhost:8080/facebook");
        oAuth2Parameters.setScope("email");
        return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(oAuth2Parameters);
    }

    @Override
    public String getAccessToken(String code) {
        return createFacebookConnection().getOAuthOperations().exchangeForAccess(code,"http://localhost:8080/facebook",null).getAccessToken();
    }

    @Override
    public User getUserProfile(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        return facebook.fetchObject("me",User.class,"email");
    }
}
