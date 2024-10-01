package com.karpo.quisy.helpers;

import com.karpo.quisy.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBuilder {
    public User one(int i) {
        User user = new User();
        user.setUserId((long) i);
        user.setNickname("UserNickname " + i);
        user.setEmail("UserEmail " + i);
        user.setAccessToken("UserAccessToken " + i);
        user.setRefreshToken("UserRefreshToken " + i);
        user.setProfileImage("UserProfileImage " + i);
        user.setActive(true);
        user.setProvider("UserProvider " + i);
        user.setProviderId("UserProviderId " + i);

        return user;
    }

    public List<User> many(int max) {
        List<User> users = new ArrayList<>();
        for(int i=0; i<max; i++) {
            users.add(one(i));
        }

        return users;
    }
}
