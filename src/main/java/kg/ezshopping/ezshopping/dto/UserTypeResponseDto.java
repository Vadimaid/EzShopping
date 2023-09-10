package kg.ezshopping.ezshopping.dto;

import kg.ezshopping.ezshopping.types.UserType;

public class UserTypeResponseDto {
    private UserType userType;

    public UserTypeResponseDto() {
    }

    public UserType getUserType() {
        return userType;
    }

    public UserTypeResponseDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
