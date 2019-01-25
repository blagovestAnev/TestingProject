package com.endava.demo.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Audited
@Table(name = "users")
public class User implements Serializable {

    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Id
    @Column(name = "login_name", nullable = false, unique = true)
    private String loginName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass_salt", nullable = false)
    private int salt;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    private String role;

}
