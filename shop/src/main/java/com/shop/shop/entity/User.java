package com.shop.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "user";

    @Column(nullable = false)
    private boolean verify = false;

    public boolean getVerify(){
        return verify;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserInformation userInformation;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Cart cart;
}
