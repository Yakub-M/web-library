package com.yakub.weblibrary.security;

import com.yakub.weblibrary.model.User;
import com.yakub.weblibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
         return new org.springframework.security.core.userdetails.User(
             user.getUsername(),
             user.getPassword(),
             user.getRoles().stream()
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList())
         );
    }
}

