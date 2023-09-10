package kg.ezshopping.ezshopping.entity;

import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
public class AppUser extends BaseEntity implements UserDetails {

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

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<AppRole> userRolesList;

    @PrePersist
    public void prePersist() {
        this.isActive = Boolean.TRUE;
        this.createdAt = LocalDateTime.now();
    }

    public AppUser() {
        this.userRolesList = new ArrayList<>();
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

    public List<AppRole> getUserRolesList() {
        return userRolesList;
    }

    public AppUser setUserRolesList(List<AppRole> userRoles) {
        this.userRolesList = userRoles;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRolesList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getLogin();
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
        return this.getActive();
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
