package project.realestate.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String nickname;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String nickname, String role, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRole(){
        return role;
    }

    // 사용자의 권한 목록 반환 메서드 (구현해야됨)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 사용자 계정의 만료여부, 일단 true 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 자격이 잠겨있는지, 일단 true 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 자격 증명 만료 여부, 일단 true 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 계정이 활성화 되어 있는지, 일단 true 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
