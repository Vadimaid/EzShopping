package kg.ezshopping.ezshopping.dto;

import kg.ezshopping.ezshopping.types.UserType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class AppUserRequestDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;

    public AppUserRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public AppUserRequestDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserRequestDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserRequestDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public AppUserRequestDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
