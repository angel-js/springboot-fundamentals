package com.fundamentosplatzi.springboot.fundamentos.configuration;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUserImplement;
import com.fundamentosplatzi.springboot.fundamentos.services.UserService;
import org.springframework.context.annotation.Bean;

public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService) {
        return new GetUserImplement(userService);

    }
}
