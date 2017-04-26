package com.oauth2.repository;

import com.oauth2.entity.UserDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author waylon on 21/03/2017.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

    @EntityGraph("UserDetails.Graph.FetchAuthorities")
    @Query("SELECT u FROM UserDetails u JOIN FETCH u.clientDetailsSet c WHERE u.username = :username AND c.clientId = :clientId")
    UserDetails findByEnabledTrueAndUsernameAndClient(@Param("username") String username, @Param("clientId") String clientId);
}
