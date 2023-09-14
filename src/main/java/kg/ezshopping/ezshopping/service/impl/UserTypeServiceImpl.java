package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.mapper.UserTypeMapper;
import kg.ezshopping.ezshopping.service.UserTypeService;
import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Override
    public List<UserTypeResponseDto> getAllUserTypes() {
        return UserTypeMapper.mapUserTypeListToUserTypeResponseDto(this.getUserTypeValuesArrayAsList());
    }

    @Override
    public List<UserTypeResponseDto> getAllNonSystemUserTypes() {
        return this.getUserTypeValuesArrayAsList()
                .stream()
                .filter(userType -> !userType.equals(SYSTEM_TYPE))
                .map(UserTypeMapper::mapUserTypeToUserTypeResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserType> getUserTypeValuesArrayAsList() {
        return Arrays.asList(UserType.values());
    }
}
