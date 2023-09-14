package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.UserTypeResponseDto;
import kg.ezshopping.ezshopping.types.UserType;

import java.util.List;
import java.util.stream.Collectors;

public class UserTypeMapper {
    public static UserTypeResponseDto mapUserTypeToUserTypeResponseDto(UserType source) {
        return new UserTypeResponseDto().setUserType(source);
    }

    public static List<UserTypeResponseDto> mapUserTypeListToUserTypeResponseDto(List<UserType> sourceList) {
        return sourceList.stream().map(UserTypeMapper::mapUserTypeToUserTypeResponseDto).collect(Collectors.toList());
    }
}
