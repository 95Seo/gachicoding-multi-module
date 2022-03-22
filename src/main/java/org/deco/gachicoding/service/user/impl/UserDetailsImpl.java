package org.deco.gachicoding.service.user.impl;

import lombok.AllArgsConstructor;
import org.deco.gachicoding.domain.user.Role;
import org.deco.gachicoding.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 추후 Enum 클래스로 수정
        Role role = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + role);
        Collection<GrantedAuthority> authorities = new ArrayList<>(); //List인 이유 : 여러개의 권한을 가질 수 있다
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
