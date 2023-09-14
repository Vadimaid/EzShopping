package kg.ezshopping.ezshopping.dto;

public class StoreRequestDto {
    private String fullName;
    private String login;
    private String password;
    private String phoneNumber;
    private String email;
    private String address;
    private String link;

    public StoreRequestDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public StoreRequestDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public StoreRequestDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public StoreRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StoreRequestDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public StoreRequestDto setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StoreRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoreRequestDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLink() {
        return link;
    }

    public StoreRequestDto setLink(String link) {
        this.link = link;
        return this;
    }
}