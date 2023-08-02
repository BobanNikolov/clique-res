package com.example.cliqueres.domain;

import com.example.cliqueres.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "CLIQUE_USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "SURNAME")
    private String surname;


    @Column(name = "PASSWORD_USER")
    private String password;

    @Column(name = "NAME_USER")
    private String name;


    @Column(name = "IS_ACCOUNT_NON_EXPIRED")
    private boolean isAccountNonExpired;
    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean isAccountNonLocked;
    @Column(name = "IS_CREDENTIALS_NON_EXPIRED")
    private boolean isCredentialsNonExpired;
    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLE_USER")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Reservation> reservations;
}

