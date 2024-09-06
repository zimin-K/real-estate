package project.realestate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.realestate.entity.Member;
import project.realestate.repository.MemberRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        // 사용자 권한(Role)을 가져와서 GrantedAuthority 객체로 변환
        GrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());

        return new CustomUserDetails(
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.getRole().name(),
                Collections.singletonList(authority)
        );

    }
}
