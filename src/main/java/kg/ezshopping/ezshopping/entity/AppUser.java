package kg.ezshopping.ezshopping.entity;

import kg.ezshopping.ezshopping.types.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class AppUser extends BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_first_name")
    private String firstName;

    @Column(name = "user_last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "is_active")
    private Boolean isActive;

    public AppUser() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AppUser setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public AppUser setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public AppUser setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public AppUser setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
