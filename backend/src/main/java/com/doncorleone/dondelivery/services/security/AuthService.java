package com.doncorleone.dondelivery.services.security;

import com.doncorleone.dondelivery.entities.User;
import com.doncorleone.dondelivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuario = Optional.ofNullable(userRepository.findByEmail(username));
        if(usuario.isPresent()) return usuario.get();

        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
