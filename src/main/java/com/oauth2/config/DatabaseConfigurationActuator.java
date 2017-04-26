package com.oauth2.config;

import com.oauth2.entity.ClientDetails;
import com.oauth2.entity.UserDetails;
import com.oauth2.repository.ClientDetailsRepository;
import com.oauth2.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author waylon on 26/04/2017.
 *
 * Class checks if valid oauth2 configuration exists in the database.
 */
@Component
public class DatabaseConfigurationActuator implements HealthIndicator {

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public Health health() {
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();

        if (clientDetailsList.isEmpty()) {
            return Health.down().status("ClientDetails repository empty. Please configure.").build();
        }
        else if (userDetailsList.isEmpty()) {
            return Health.down().status("UserDetails repository empty. Please configure.").build();
        }
        else {
            return Health.up().build();
        }
    }
}
