package com.oauth2.repository;

import com.oauth2.entity.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author waylon on 21/03/2017.
 */
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, String> {

    ClientDetails findByClientId(String clientId);
}
