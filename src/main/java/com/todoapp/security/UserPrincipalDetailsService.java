package com.todoapp.security;

import com.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (userRepository.findByUsername(s) != null){
            UserPrincipal userPrincipal = new UserPrincipal(userRepository.findByUsername(s));
            return userPrincipal;
        }
        throw  new UsernameNotFoundException(s);
    }
}
