package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //http 요청에 대한 보안을 설정합니다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드등에 대한 설정을 작성합니다
    @Override
    protected void configure(HttpSecurity http) throws Exception{}


    //비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터 베이스가 해킹당하면 고객의 회원 정보가 그대로 노출됩니다.
    //이를 해결하기 위한 BCryptPasswordEncoder의 해쉬 함수를 이용하여 비밀번호를 암호화하여 저장
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
