package com.example.finances.service;

import com.example.finances.model.User;
import com.example.finances.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo; this.encoder = encoder;
    }

    public User register(String name, String email, String rawPassword){
        if (repo.existsByEmail(email)) throw new RuntimeException("Email já cadastrado");
        User u = new User(name, email, encoder.encode(rawPassword));
        return repo.save(u);
    }

    public User findByEmail(String email){
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = findByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
