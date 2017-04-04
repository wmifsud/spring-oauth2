package com.oauth2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author waylon on 21/03/2017.
 */
@Entity
@Table(name = "client_details", schema = "external")
public class ClientDetails
{
    private Long id;
    private String clientId;
    private String secret;
    private String scope;
    private String authorizedGrantTypes;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "clientDetailsGenerator")
    @GenericGenerator
    (
        name = "clientDetailsGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = @Parameter(name = "sequence_name", value = "external.client_details_id_seq")
    )
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "client_id")
    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    @Column(name = "secret")
    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    @Column(name = "scope")
    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    @Column(name = "authorized_grant_types")
    public String getAuthorizedGrantTypes()
    {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes)
    {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    @Column(name = "access_token_validity")
    public Integer getAccessTokenValidity()
    {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity)
    {
        this.accessTokenValidity = accessTokenValidity;
    }

    @Column(name = "refresh_token_validity")
    public Integer getRefreshTokenValidity()
    {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity)
    {
        this.refreshTokenValidity = refreshTokenValidity;
    }
}
