package com.roki.purchase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String password;

    private Boolean enabled;

    private String firstName;

    private String lastName;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<AuthorityEntity> authorityList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AuthorityEntity> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<AuthorityEntity> authorityList) {
        this.authorityList = authorityList;
    }
}
