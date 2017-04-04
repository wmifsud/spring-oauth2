package com.oauth2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author waylon on 21/03/2017.
 */
@NamedEntityGraph
(
    name = "UserDetails.Graph.FetchAuthorities",
    attributeNodes =
    {
        @NamedAttributeNode(value = "authoritySet")
    }
)
@Entity
@Table(name = "user_details", schema = "external")
public class UserDetails
{
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<ClientDetails> clientDetailsSet;
    private Set<Authority> authoritySet;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "userDetailsGenerator")
    @GenericGenerator
    (
        name = "userDetailsGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = @Parameter(name = "sequence_name", value = "external.user_details_id_seq")
    )
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "username")
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Column(name = "enabled")
    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_details_user_details_map",
            schema = "external",
            joinColumns =
            @JoinColumn(name = "user_details_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "client_details_id", referencedColumnName = "id"))
    public Set<ClientDetails> getClientDetailsSet()
    {
        return clientDetailsSet;
    }

    public void setClientDetailsSet(Set<ClientDetails> clientDetailsSet)
    {
        this.clientDetailsSet = clientDetailsSet;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_details_authority_map",
            schema = "external",
            joinColumns =
            @JoinColumn(name = "user_details_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    public Set<Authority> getAuthoritySet()
    {
        return authoritySet;
    }

    public void setAuthoritySet(Set<Authority> authoritySet)
    {
        this.authoritySet = authoritySet;
    }
}
