package com.auth.authservice.service;

import com.auth.authservice.entity.User;
import com.auth.authservice.repo.UserRepo;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("User not Found");
        }
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            System.out.println("ROLE===="+user.getRoles().iterator().next().getName().toString());
            //builder.roles(user.getRoles().iterator().next().getName().toString(),"ADMIN");
            builder.roles(user.getRoles().iterator().next().getName().toString());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    //Ref
    //https://www.appsdeveloperblog.com/spring-security-preauthorize-annotation-example/
    //https://www.toptal.com/spring/spring-security-tutorial
    //https://www.marcobehler.com/guides/spring-security
}
