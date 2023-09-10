package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.AppUserTestDtoProvider;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.AppUserTestEntityProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserMapperTest {
    @Test
    public void testMapToAppUserEntity_OK() {
        AppUserRequestDto requestDto = AppUserTestDtoProvider.getAppUserTestRequestDto();

        AppUser result = AppUserMapper.mapToAppUserEntity(requestDto);
        Assertions.assertEquals(requestDto.getLogin(), result.getLogin());
        Assertions.assertEquals(requestDto.getPassword(), result.getPassword());
        Assertions.assertEquals(requestDto.getFirstName(), result.getFirstName());
        Assertions.assertEquals(requestDto.getLastName(), result.getLastName());
        Assertions.assertEquals(requestDto.getUserType(), result.getUserType());
    }

    @Test
    public void testMapEntityToAppUserResponseDto_OK() {
        AppUser appUser =
                AppUserTestEntityProvider.getAppUserTestEntity(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);

        AppUserResponseDto result = AppUserMapper.mapEntityToAppUserResponseDto(appUser);
        Assertions.assertEquals(appUser.getId(), result.getId());
        Assertions.assertEquals(appUser.getLogin(), result.getLogin());
        Assertions.assertEquals(appUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(appUser.getLastName(), result.getLastName());
        Assertions.assertEquals(appUser.getCreatedAt(), result.getCreatedAt());
        Assertions.assertEquals(appUser.getUserType(), result.getUserType());
    }

    @Test
    public void testMapEntityListToAppUserResponseDtoList_OK() {
        List<AppUser> appUserList = AppUserTestEntityProvider
                .getAppUserTestEntityList(AppUserTestEntityProvider.APP_USER_TEST_RAW_PASSWORD);

        List<AppUserResponseDto> resultList = AppUserMapper.mapEntityListToAppUserResponseDtoList(appUserList);

        Assertions.assertEquals(appUserList.size(), resultList.size());
        for (int i = 0; i < appUserList.size(); i++) {
            Assertions.assertEquals(appUserList.get(i).getId(), resultList.get(i).getId());
            Assertions.assertEquals(appUserList.get(i).getLogin(), resultList.get(i).getLogin());
            Assertions.assertEquals(appUserList.get(i).getFirstName(), resultList.get(i).getFirstName());
            Assertions.assertEquals(appUserList.get(i).getLastName(), resultList.get(i).getLastName());
            Assertions.assertEquals(appUserList.get(i).getCreatedAt(), resultList.get(i).getCreatedAt());
            Assertions.assertEquals(appUserList.get(i).getUserType(), resultList.get(i).getUserType());
        }
    }

}