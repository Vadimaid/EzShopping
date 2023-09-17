package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.ClientUserRegisterDto;
import kg.ezshopping.ezshopping.dto.ShopUserRegisterDto;
import kg.ezshopping.ezshopping.entity.AppRole;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.exception.InvalidUserCredentialsException;
import kg.ezshopping.ezshopping.exception.LoginAlreadyExistsException;
import kg.ezshopping.ezshopping.mapper.AppUserMapper;
import kg.ezshopping.ezshopping.repository.AppRoleRepository;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.types.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.Collections;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final String ROLE_SHOP = "SHOP";
    private final String ROLE_CLIENT = "CLIENT";

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public AppUserServiceImpl(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public AppUserResponseDto getCurrentAuthorizedUser() {
        AppUser authorizedAppUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return AppUserMapper.mapEntityToAppUserResponseDto(authorizedAppUser);
    }

    @Transactional
    @Override
    public AppUserResponseDto registerNewClient(ClientUserRegisterDto requestDto) throws LoginAlreadyExistsException, InvalidUserCredentialsException {
        if (requestDto.getLogin().length() < 5) {
            throw new InvalidUserCredentialsException("Логин не должен быть короче 5 символов");
        }

        if (requestDto.getPassword().length() < 8) {
            throw new InvalidUserCredentialsException("Пароль не должен содержать менее 8 символов");
        }

        Optional<AppUser> possibleDuplicate = this.appUserRepository.getAppUserByLogin(requestDto.getLogin());
        if (possibleDuplicate.isPresent()) {
            throw new LoginAlreadyExistsException("Этот логин уже занят.");
        }

        AppRole role = this.appRoleRepository.findByName(this.ROLE_CLIENT);
        AppUser client = new AppUser();
        client
                .setLogin(requestDto.getLogin())
                .setPassword(this.passwordEncoder.encode(requestDto.getPassword()))
                .setUserType(UserType.CLIENT)
                .setUserFullName(requestDto.getFullName())
                .setDateOfBirth(requestDto.getDateOfBirth())
                .setEmail(requestDto.getEmail())
                .setUserRolesList(Collections.singletonList(role))
                .setProfileImage(Base64Utils.decodeFromString(requestDto.getProfileImageBase64()));

        this.appUserRepository.save(client);
        AppUserResponseDto response = new AppUserResponseDto();
        response
                .setId(client.getId())
                .setCreatedAt(client.getCreatedAt())
                .setLogin(client.getLogin())
                .setFullName(client.getUserFullName())
                .setUserType(client.getUserType())
                .setProfileImage(Base64Utils.encodeToString(client.getProfileImage()));
        return response;
    }

    @Transactional
    @Override
    public AppUserResponseDto registerNewShop(ShopUserRegisterDto requestDto) throws LoginAlreadyExistsException, InvalidUserCredentialsException {
        if (requestDto.getLogin().length() < 5) {
            throw new InvalidUserCredentialsException("Логин не должен быть короче 5 символов");
        }

        if (requestDto.getPassword().length() < 8) {
            throw new InvalidUserCredentialsException("Пароль не должен содержать менее 8 символов");
        }

        Optional<AppUser> possibleDuplicate = this.appUserRepository.getAppUserByLogin(requestDto.getLogin());
        if (possibleDuplicate.isPresent()) {
            throw new LoginAlreadyExistsException("Этот логин уже занят.");
        }

        AppRole role = this.appRoleRepository.findByName(this.ROLE_SHOP);
        AppUser shop = new AppUser();
        shop
                .setLogin(requestDto.getLogin())
                .setPassword(this.passwordEncoder.encode(requestDto.getPassword()))
                .setUserType(UserType.SHOP)
                .setUserFullName(requestDto.getFullName())
                .setAddress(requestDto.getAddress())
                .setInstagramAccount(requestDto.getInstagramAccount())
                .setPhone(requestDto.getPhone())
                .setProfileImage(Base64Utils.decodeFromString(requestDto.getProfileImageBase64()));

        this.appUserRepository.save(shop);
        AppUserResponseDto response = new AppUserResponseDto();
        response
                .setId(shop.getId())
                .setCreatedAt(shop.getCreatedAt())
                .setLogin(shop.getLogin())
                .setFullName(shop.getUserFullName())
                .setUserType(shop.getUserType())
                .setProfileImage(Base64Utils.encodeToString(shop.getProfileImage()));
        return response;
    }

    @Override
    public AppUser getAuthorizedUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
