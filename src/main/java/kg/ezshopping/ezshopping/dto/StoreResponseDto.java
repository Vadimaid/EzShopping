package kg.ezshopping.ezshopping.dto;

import java.time.LocalDateTime;

public class StoreResponseDto {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String login;
    private String address;
    private String link;
    private LocalDateTime createdAt;
    private Boolean isActive;

    public Boolean getActive() {
        return isActive;
    }

    public StoreResponseDto setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public StoreResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public StoreResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public StoreResponseDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StoreResponseDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public StoreResponseDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getLink() {
        return link;
    }

    public StoreResponseDto setLink(String link) {
        this.link = link;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StoreResponseDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoreResponseDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public StoreResponseDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
