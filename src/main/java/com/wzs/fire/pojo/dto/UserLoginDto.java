package com.wzs.fire.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.wzs.fire.pojo.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto implements UserDetails,Serializable{

    private Users user;

    private Integer role_id;

    private String token;

    private List<String> permission;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authority;

    public UserLoginDto(Users user, List<String> list) {
        this.user = user;
        this.permission = list;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoginDto(Users user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authority != null){
            return authority;
        }
        authority = permission.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authority;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
