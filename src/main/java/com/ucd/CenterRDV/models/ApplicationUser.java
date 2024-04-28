package com.ucd.CenterRDV.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(unique = true)
    @NotBlank(message = "vouilez vous saisir votre email")
    @Email(message = "saisir un email valid (ex: xyz@xyz.com)")
    private String userName;

    @Column(name = "first_name")
    @NotBlank(message = "vouilez vous saisir votre prénom")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "vouilez vous saisir votre nom")
    private String lastName;

    @Column(name = "phone_number")
    @NotBlank(message = "vouilez vous saisir votre numero de telephone")
    private String phoneNumber;

    @NotBlank(message = "vouilez vous saisir votre mot de passe")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    @OneToMany(
            mappedBy = "user"
    )
    @JsonManagedReference
    private Set<RDV> rdvs;

//    @ManyToOne
//    @JoinColumn(name = "centre_id")
//    private Centre centreAdmin;

    public ApplicationUser(Integer userId, String userName, String firstName, String lastName, String phoneNumber, String password, Set<Role> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authorities = authorities;
    }

    public ApplicationUser() {
        super();
        this.authorities = new HashSet<Role>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}