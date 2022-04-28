package com.shop.entity;


import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    //회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 데이터베이스에 들어올수 없도록 unique를 설정
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    //자바의 enum 타입을 엔티티의 속성을 지정할 수 있습니다. enum을 사용할 때 기본적으로 순서가 저장되는데, enum의 순서가 바뀐경우 문자게 발생할 수 있으므로
    //(EnumType.STRING) 옵션을 사용해서 String으로 지정하기 권장
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
    //  Member 엔티티를 생성하는 메소드 입니다. Member엔티티에 회원을 생성하는 메소드를 만들어서
        //  관리한다면 코드가 변경되도라도 한 군데만 수정하는 되는 이점이 있습니다.

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        // 스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean을 파라미터로 넘겨서 비밀번호를 함호화 합니다.
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        member.setPassword(password);
        member.setRole(Role.ADMIN);

        return member;
    }
}
