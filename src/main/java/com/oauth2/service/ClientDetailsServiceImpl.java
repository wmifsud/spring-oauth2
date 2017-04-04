package com.oauth2.service;

import com.oauth2.entity.ClientDetails;
import com.oauth2.repository.ClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author waylon on 22/03/2017.
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService
{
    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    public org.springframework.security.oauth2.provider.ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException
    {
        ClientDetails client = clientDetailsRepository.findByClientId(clientId);

        if(client == null)
        {
            throw new ClientRegistrationException("Client not found for clientId: " + clientId);
        }

        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId(client.getClientId());
        baseClientDetails.setClientSecret(client.getSecret());
        baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        baseClientDetails.setScope(Arrays.asList(client.getScope().split(",")));
        baseClientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        baseClientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());

        return baseClientDetails;
    }
}