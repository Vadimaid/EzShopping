package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import kg.ezshopping.ezshopping.types.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTypeMapperTest {

    @Test
    public void testMapUserTypeToUserTypeResponseDto_OK() {
        UserType testUserType = AppUserTestEntityProvider.APP_USER_TEST_USER_TYPE;
        UserTypeResponseDto userTypeResponseDto = UserTypeMapper.mapUserTypeToUserTypeResponseDto(testUserType);
        Assertions.assertEquals(testUserType, userTypeResponseDto.getUserType());
    }

    @Test
    public void testMapUserTypeListToUserTypeResponseDto_OK() {
        List<UserType> testUserTypesList = Arrays.asList(UserType.values());

        List<UserTypeResponseDto> resultList = UserTypeMapper.mapUserTypeListToUserTypeResponseDto(testUserTypesList);
        Assertions.assertEquals(testUserTypesList.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            Assertions.assertEquals(testUserTypesList.get(i), resultList.get(i).getUserType());
        }
    }
}