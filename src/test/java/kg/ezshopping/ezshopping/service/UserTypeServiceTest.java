package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.service.impl.UserTypeServiceImpl;
import kg.ezshopping.ezshopping.types.UserType;
import kg.ezshopping.ezshopping.utils.UserTypeAssertionsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserTypeServiceTest {
    private UserTypeService userTypeService;

    @BeforeEach
    public void beforeEach() {
        this.userTypeService = new UserTypeServiceImpl();
    }

    @Test
    public void testGetAllUserTypes_OK() {
        List<UserType> userTypeList = this.userTypeService.getUserTypeValuesArrayAsList();

        List<UserTypeResponseDto> resultList = this.userTypeService.getAllUserTypes();
        Assertions.assertEquals(userTypeList.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            Assertions.assertEquals(userTypeList.get(i), resultList.get(i).getUserType());
        }
    }

    @Test
    public void testGetAllNotSystemUserTypes_OK() {
        List<UserTypeResponseDto> resultList = this.userTypeService.getAllNonSystemUserTypes();
        Assertions.assertTrue(UserTypeAssertionsUtils.assertThatNotContainsSystemType(resultList));
    }
}