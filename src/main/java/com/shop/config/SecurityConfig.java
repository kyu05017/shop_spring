package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    //http 요청에 대한 보안을 설정합니다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드등에 대한 설정을 작성합니다
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //로그인 페이지 URL을 설정합니다
                .loginPage("/members/login")
                //로그인에 성공할시 이동할 URL을 설정합니다
                .defaultSuccessUrl("/")
                //로그인에 성공할 경우 파라미터 이름으로 email을 지정합니다.
                .usernameParameter("email")
                //로그인 실패시 이동할 페이지를 지정합니다
                .failureUrl("/members/login/error")
                .and()
                .logout()
                // 로그아웃  URL 을 지정합니다
                .logoutRequestMatcher(new AntPathRequestMatcher("members/logout"))
                // 로그아웃 성공시 이동할 URL을 지정합니다.
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
                //시큐리티 처리에 httpServletRequests 이용한다는 것을 의미합니다.
                .mvcMatchers("/","/members/**","/item/**","/images/**").permitAll()
                //permitAll()을 통해 모든 사용자가 인증없이 해당 경로에 접근할수 있게 설정 합니다,.
                // 메인페이지 회원 관련 URL, 뒤에서 만들 상품 상세 페이지, 상품 이미지를 불러오는
                // 경로가 이에 해당합니다.
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // /admin으로 시작하는 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정합니다.
                .anyRequest().authenticated()
                //
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(
                        new CustomAuthenticationEntryPoint()
                )
        ;
    }

    //비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터 베이스가 해킹당하면 고객의 회원 정보가 그대로 노출됩니다.
    //이를 해결하기 위한 BCryptPasswordEncoder의 해쉬 함수를 이용하여 비밀번호를 암호화하여 저장
    @Bean // auto
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  Spring Security에서 인증은 AuthenticationManager를 통해 이뤄지며 AuthenticationManagerBuilder
        // AuthenticationManager를 생성합니다. userDetailsService 구현하고 있는 객체로 memberService를 지정해주며,
        // 비밀번호 암호화를 위해 passwordEncoder를 지정합니다.
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        // static 디렉터리의 하위 파일은 인증을 무시하도록 설정
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }
}
