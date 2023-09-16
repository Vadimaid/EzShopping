package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import org.springframework.util.Base64Utils;

public class AppUserMapper {

    public static AppUserResponseDto mapEntityToAppUserResponseDto(AppUser source) {
        return new AppUserResponseDto()
                .setId(source.getId())
                .setLogin(source.getLogin())
                .setFullName(source.getUserFullName())
                .setUserType(source.getUserType())
                .setCreatedAt(source.getCreatedAt())
                .setProfileImage(Base64Utils.encodeToString(source.getProfileImage()));
    }
}
