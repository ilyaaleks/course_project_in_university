package org.belstu.fakegram.FakeGram.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOauthKeys {
    public static final String ID = "sub";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";
    public static final String LOCALE = "locale";
    public static final String PICTURE = "picture";
}
