package com.poliakov.springbootconference.configuration;

import java.util.Optional;

import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = usersRepository.findByEmail(userName);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String[] roles = new String[]{user.getRole() + ""};
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }
}