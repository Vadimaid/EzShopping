package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.types.UserType;

import java.util.List;

public interface UserTypeService {
    UserType SYSTEM_TYPE = UserType.SYSTEM;

    List<UserTypeResponseDto> getAllUserTypes();

    List<UserTypeResponseDto> getAllNonSystemUserTypes();

    List<UserType> getUserTypeValuesArrayAsList();
}
