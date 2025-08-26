package ch.zmotions.linkit.service.impl;

import ch.zmotions.linkit.service.LoginAttemptService;
import ch.zmotions.linkit.service.domain.RoleEO;
import ch.zmotions.linkit.service.domain.UserEO;
import ch.zmotions.linkit.service.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginAttemptService loginAttemptService;
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(LoginAttemptService loginAttemptService, UserRepository userRepository) {
        this.loginAttemptService = loginAttemptService;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        String ip = getClientIp();
        if (ip == null) ip = username;
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        Optional<UserEO> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) throw new UsernameNotFoundException(username);
        UserEO user = optionalUser.get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleEO role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true,
                user.getAccountNonLocked(), grantedAuthorities);
    }

    private String getClientIp() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            String xfHeader = request.getHeader("X-Forwarded-For");
            if (xfHeader == null) {
                return request.getRemoteAddr();
            }
            return xfHeader.split(",")[0];
        }
        return null;
    }
}