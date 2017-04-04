package com.oauth2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author waylon on 22/03/2017.
 */
@Entity
@Table(name = "authority", schema = "oauth2")
public class Authority
{
    private Long id;
    private String authority;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "authorityGenerator")
    @GenericGenerator
    (
        name = "authorityGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = @Parameter(name = "sequence_name", value = "oauth2.authority_id_seq")
    )
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "authority")
    public String getAuthority()
    {
        return authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }
}
