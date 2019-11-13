package com.softserve.ita.java442.cityDonut.security.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.social.google.api.ApiEntity;

@Data
public class UserInfo extends ApiEntity {

    @JsonProperty
    private String name;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String picture;

    @JsonProperty
    private String link;

    @JsonProperty
    private String email;

    @JsonProperty("verified_email")
    private String verifiedEmail;

    @JsonProperty
    private String hd;

    @JsonProperty
    private String locale;
}