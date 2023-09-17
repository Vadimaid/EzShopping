package kg.ezshopping.ezshopping.entity;

import kg.ezshopping.ezshopping.types.UserType;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "instagram_account")
    private String instagramAccount;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "profile_image")
    private byte[] profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<AppRole> userRolesList;

    @ManyToMany
    @JoinTable(
            name = "user_follower",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follow_id", referencedColumnName = "id")
    )
    private List<AppUser> followedUsers;

    @OneToMany(mappedBy = "user")
    private List<UserItem> items;

    @OneToMany(mappedBy = "user")
    private List<UserShoppingHistory> shoppingHistories;

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

    public String getUserFullName() {
        return userFullName;
    }

    public AppUser setUserFullName(String userFullName) {
        this.userFullName = userFullName;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public AppUser setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AppUser setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AppUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getInstagramAccount() {
        return instagramAccount;
    }

    public AppUser setInstagramAccount(String instagramAccount) {
        this.instagramAccount = instagramAccount;
        return this;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public AppUser setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public List<AppUser> getFollowedUsers() {
        return followedUsers;
    }

    public AppUser setFollowedUsers(List<AppUser> followedUsers) {
        this.followedUsers = followedUsers;
        return this;
    }

    public List<UserItem> getItems() {
        return items;
    }

    public AppUser setItems(List<UserItem> items) {
        this.items = items;
        return this;
    }

    public List<UserShoppingHistory> getShoppingHistories() {
        return shoppingHistories;
    }

    public AppUser setShoppingHistories(List<UserShoppingHistory> shoppingHistories) {
        this.shoppingHistories = shoppingHistories;
        return this;
    }
}
