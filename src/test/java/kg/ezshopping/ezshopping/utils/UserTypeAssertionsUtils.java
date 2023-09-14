package kg.ezshopping.ezshopping.utils;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.service.UserTypeService;

import java.util.List;
import java.util.Objects;

public class UserTypeAssertionsUtils {
    public static boolean assertThatNotContainsSystemType(
            List<UserTypeResponseDto> userTypeResponseDtoList
    ) {
        if(Objects.isNull(userTypeResponseDtoList) || userTypeResponseDtoList.isEmpty()) {
            throw new IllegalArgumentException("Список типов пользователей не может быть null или пустым!");
        }
        for (UserTypeResponseDto userTypeResponseDto : userTypeResponseDtoList) {
            if(userTypeResponseDto.getUserType().equals(UserTypeService.SYSTEM_TYPE)) {
                return false;
            }
        }
        return true;
    }
}
