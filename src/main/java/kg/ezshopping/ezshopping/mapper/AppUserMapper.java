package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {
    public static AppUser mapToAppUserEntity(AppUserRequestDto appUserRequestDto) {
        return new AppUser()
                .setLogin(appUserRequestDto.getLogin())
                .setPassword(appUserRequestDto.getPassword())
                .setFirstName(appUserRequestDto.getFirstName())
                .setLastName(appUserRequestDto.getLastName())
                .setUserType(appUserRequestDto.getUserType());
    }

    public static AppUserResponseDto mapEntityToAppUserResponseDto(AppUser source) {
        return new AppUserResponseDto()
                .setId(source.getId())
                .setLogin(source.getLogin())
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName())
                .setUserType(source.getUserType())
                .setCreatedAt(source.getCreatedAt());
    }

    public static List<AppUserResponseDto> mapEntityListToAppUserResponseDtoList(List<AppUser> sourceList) {
        return sourceList
                .stream()
                .map(AppUserMapper::mapEntityToAppUserResponseDto)
                .collect(Collectors.toList());
    }
}
