package org.hassen.hopital.security.service;

import org.hassen.hopital.security.entities.AppUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers appUser= securityService.LoadUserByUserName(username);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.
                getAppRoles().
                forEach(role->{
                    SimpleGrantedAuthority authority= new SimpleGrantedAuthority(role.getRoleName());
                    authorities.add(authority);
                });
        String userName=appUser.getUserName();
        String pwd=appUser.getPassword();
        User user= new User(userName,pwd,authorities);
        /*
        // une deuxième méthode enutilisant l'API stream
        Collection<GrantedAuthority> authorities=
                appUser.getAppRoles()
                        .stream()
                        .map(role-> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList());
         */
        return user;
    }
}
