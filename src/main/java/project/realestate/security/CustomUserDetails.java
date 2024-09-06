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

    // 사용자의 권한 목록 반환 메서드
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

}
