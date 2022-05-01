package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
// mockMVC 테스트를 위해 어노테이션 선언
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations ="classpath:application-test.properties")
class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;
    //MockMvc를 이용해 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜객체입니다. MockMVc객체를 이용하면
    // 웹 브라우져에서 요청하느 것처럼 테스트 가능 합니다.

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email,String password) {
    // 회원가입을 하는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail(email);
        memberFormDto.setName("김규석");
        memberFormDto.setAddress("안양시 만안구 안양동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto,passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {

        String email = "test@naver.com";
        String password = "1234";

        this.createMember(email,password);
        mockMvc.perform(formLogin().userParameter("email")
                // 회원가입 메소드를 실행 후 가입된 회원 정보로 로그인이 되는지 테스트를 진행 합니다.
                //userParameter()를 이용하여 이메일을 아이디로 세팅하고 로그인 URL에 용청
                        .loginProcessingUrl("/members/login")
                        .user(email).password(password))
                //로그인이 성공하여 인증되었다면 테스트 코드가 통과합니다.
                        .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@naver.com";
        String password = "1234";

        this.createMember(email,password);


        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
        //  회원가입은 정장적으로 진행 되었지만, 회원가입시 입력한 비밀번호가 아닌 다른 비밀번호로 로그인 시도하여 인증되지
        // 않은 결과 값이 출력되어 테스트 합니다.
    }
}
