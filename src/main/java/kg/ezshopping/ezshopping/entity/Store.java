package kg.ezshopping.ezshopping.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address")
    private String address;
    @Column(name = "link")
    private String link;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        this.isActive = Boolean.TRUE;
        this.createdAt = LocalDateTime.now();
    }

    public Store() {
    }

    public Long getId() {
        return id;
    }

    public Store setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Store setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Store setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Store setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }



    public String getEmail() {
        return email;
    }

    public Store setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Store setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Store setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Store setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Store setLink(String link) {
        this.link = link;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Store setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
