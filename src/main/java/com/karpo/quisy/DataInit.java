package com.karpo.quisy;

import com.karpo.quisy.entities.*;
import com.karpo.quisy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataInit implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User adminUser = User.builder()
                .nickname("Karpo")
                .email("dhkdwk1041@gmail.com")
                .accessToken("accesstoken")
                .refreshToken("refreshtoken")
                .profileImage("")
                .isActive(true)
                .provider("admin")
                .providerId("admin01")
                .build();

        userRepository.save(adminUser);
    }
}
