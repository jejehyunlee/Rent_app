package com.mbgm.Rent_Manage_System.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetail implements UserDetails {

   private String username;
   private String password;
   private List<GrantedAuthority> authorities;

   public CustomUserDetail(User user){
       this.username = user.getUsername();
       this.password = user.getPassword();
       this.authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
   }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
