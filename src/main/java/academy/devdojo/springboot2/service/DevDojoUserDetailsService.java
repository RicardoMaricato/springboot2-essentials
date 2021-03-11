package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.DevDojoUser;
import academy.devdojo.springboot2.repository.DevDojoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevDojoUserDetailsService implements UserDetailsService {

    private final DevDojoRepository devDojoRepository;

    public List<DevDojoUser> findAll() {
        return devDojoRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(devDojoRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found"));
    }
}
