package project.realestate.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.realestate.constant.Role;
import project.realestate.entity.Member;
import project.realestate.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsertAdminMemberTest {

    @Autowired
    private MemberRepository memberRepository;

    //@Test
    public void insertAdmin(){
        // given
        Member member = new Member();
        member.insertAdmin("관리자명", "admin@admin.com", "1234", "관리자", Role.ADMIN);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.insertAdmin("관리자명", "admin@admin.com", encodePassword, "관리자", Role.ADMIN);

        // when
        memberRepository.save(member);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).extracting(Member::getEmail).contains("admin@admin.com");
    }

}
