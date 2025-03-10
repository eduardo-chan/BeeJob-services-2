package mx.edu.beecker.security.service;

import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUser userRepository;

    public UserDetailsServiceImpl(IUser userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        BeanUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        if (!user.isStatus()) {
            throw new DisabledException("User account is disabled");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}