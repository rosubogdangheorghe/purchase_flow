package com.roki.purchase.entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
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

    @Email
    private String email;

    private Date passwordChangeTime;

    private static final long PASSWORD_EXPIRATION_PERIOD = 30L*24L*60L*60L*1000L;

    @OneToMany(mappedBy = "user")
    private List<AuthorityEntity> authorityList;

    @OneToMany(mappedBy ="user")
    private List<PurchaseHeaderEntity> purchaseHeaderList;

    @OneToMany(mappedBy = "user")
    private List<DepartmentEntity> departmentsList;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<PurchaseHeaderEntity> getPurchaseHeaderList() {
        return purchaseHeaderList;
    }

    public void setPurchaseHeaderList(List<PurchaseHeaderEntity> purchaseHeaderList) {
        this.purchaseHeaderList = purchaseHeaderList;
    }

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

    public Date getPasswordChangeTime() {
        return passwordChangeTime;
    }

    public void setPasswordChangeTime(Date passwordChangeTime) {
        this.passwordChangeTime = passwordChangeTime;
    }

    public List<DepartmentEntity> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<DepartmentEntity> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public boolean isCredentialsNonExpired() {
        if(this.passwordChangeTime == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long lastChangedTime = this.passwordChangeTime.getTime();
        return currentTime < lastChangedTime+PASSWORD_EXPIRATION_PERIOD;
    }

    public boolean isEnabled() {
        if(this.enabled) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordChangeTime=" + passwordChangeTime +

                '}';
    }
}
