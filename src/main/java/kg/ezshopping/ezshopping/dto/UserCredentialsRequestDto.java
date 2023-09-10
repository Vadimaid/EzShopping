package kg.ezshopping.ezshopping.dto;

public class UserCredentialsRequestDto {
    private String login;
    private String password;

    public UserCredentialsRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public UserCredentialsRequestDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentialsRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
