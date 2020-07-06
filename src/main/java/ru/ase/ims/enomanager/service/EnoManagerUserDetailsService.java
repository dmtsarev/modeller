package ru.ase.ims.enomanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.model.UserIMS;
import ru.ase.ims.enomanager.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class EnoManagerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName)
                .map(UserIMS::new)
                .orElse(null);
    }
}
