package kg.ezshopping.ezshopping.dto;

import kg.ezshopping.ezshopping.types.UserType;

import java.time.LocalDateTime;

public class AppUserResponseDto {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private UserType userType;
    private LocalDateTime createdAt;

    public AppUserResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AppUserResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public AppUserResponseDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserResponseDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserResponseDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public AppUserResponseDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AppUserResponseDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
