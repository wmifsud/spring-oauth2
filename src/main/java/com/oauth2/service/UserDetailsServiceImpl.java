package com.oauth2.service;

import com.oauth2.entity.Authority;
import com.oauth2.entity.UserDetails;
import com.oauth2.repository.ClientDetailsRepository;
import com.oauth2.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author waylon on 22/03/2017.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsRepository.findByEnabledTrueAndUsernameAndClient(username, getClientIdFromSecurityContext());

        if (userDetails == null) {
            throw new UsernameNotFoundException("No user found for: " + username);
        }

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
        for (Authority authority : userDetails.getAuthoritySet()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new User(userDetails.getUsername(), userDetails.getPassword(), grantedAuthorities);
    }

    private String getClientIdFromSecurityContext() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) a);
        User user = (User) token.getPrincipal();

        return user.getUsername();
    }
}
