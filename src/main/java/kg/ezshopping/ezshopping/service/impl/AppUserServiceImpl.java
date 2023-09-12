package kg.ezshopping.ezshopping.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.ezshopping.ezshopping.dto.AppUserRequestDto;
import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.entity.QAppUser;
import kg.ezshopping.ezshopping.exception.*;
import kg.ezshopping.ezshopping.mapper.AppUserMapper;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.types.UserType;
import kg.ezshopping.ezshopping.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserValidator appUserValidator;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(
            AppUserValidator appUserValidator,
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.appUserValidator = appUserValidator;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserResponseDto registerNewAppUser(AppUserRequestDto requestDto)
            throws InvalidUserInfoException,
            InvalidUserTypeException,
            LoginAlreadyExistsException,
            InvalidUserCredentialsException
    {
        this.appUserValidator.validateAppUserRequestDto(requestDto);
        AppUser appUser = AppUserMapper.mapToAppUserEntity(requestDto);

        appUser.setPassword(this.passwordEncoder.encode(appUser.getPassword()));

        appUser = this.appUserRepository.save(appUser);
        return AppUserMapper.mapEntityToAppUserResponseDto(appUser);
    }

    @Override
    public AppUserResponseDto getCurrentAuthorizedUser() {
        AppUser authorizedAppUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return AppUserMapper.mapEntityToAppUserResponseDto(authorizedAppUser);
    }

    @Override
    public List<AppUserResponseDto> getAllAppUsers(
            Long appUserId,
            String login,
            UserType userType,
            LocalDateTime startDate,
            LocalDateTime endDate
    )
            throws InvalidIdException, IncorrectDateFiltersException, AppUsersNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QAppUser root = QAppUser.appUser;

        if(Objects.nonNull(appUserId)) {
            if(appUserId < 1L) {
                throw new InvalidIdException("ID пользователя не может быть меньше 1!");
            }
            booleanBuilder.and(root.id.eq(appUserId));
        }
        if(Objects.nonNull(login) && !login.isEmpty()) {
            booleanBuilder.and(root.login.eq(login));
        }
        if(Objects.nonNull(userType)) {
            booleanBuilder.and(root.userType.eq(userType));
        }

        boolean startDateIsNonNull = Objects.nonNull(startDate);
        if(startDateIsNonNull) {
            booleanBuilder.and(root.createdAt.goe(startDate));
        }
        if(Objects.nonNull(endDate)) {
            if(startDateIsNonNull && startDate.isAfter(endDate)) {
                throw new IncorrectDateFiltersException(
                        "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!"
                );
            }
            booleanBuilder.and(root.createdAt.loe(endDate));
        }

        Iterable<AppUser> foundAppUsersIterable;
        Predicate searchPredicate = booleanBuilder.getValue();
        if(Objects.nonNull(searchPredicate)) {
            foundAppUsersIterable = this.appUserRepository.findAll(booleanBuilder.getValue());
        } else {
            foundAppUsersIterable = this.appUserRepository.findAll();
        }

        List<AppUserResponseDto> appUserResponseDtoList =
                StreamSupport
                        .stream(foundAppUsersIterable.spliterator(), false)
                        .map(AppUserMapper::mapEntityToAppUserResponseDto).toList();

        if(appUserResponseDtoList.isEmpty()) {
            throw new AppUsersNotFoundException("По заданным параметрам не найдено ни одного пользователя!");
        }
        return appUserResponseDtoList;
    }

    @Override
    public AppUserResponseDto updateUserInfo(
            String oldPassword,
            String newPassword,
            String newFirstName,
            String newLastName,
            UserType userType,
            Boolean isActive
    ) throws WrongPasswordException
    {
        AppUser authorizedAppUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if(Objects.nonNull(oldPassword) && !oldPassword.isEmpty()){
            if(!passwordEncoder.matches(oldPassword,authorizedAppUser.getPassword())){
                throw new WrongPasswordException("Введен неверный пароль!");
            }
        }

        if(Objects.nonNull(newPassword) && !newPassword.isEmpty())  {
            authorizedAppUser.setPassword(passwordEncoder.encode(newPassword));
        }
        if(Objects.nonNull(newFirstName) && !newFirstName.isEmpty())  {
            authorizedAppUser.setFirstName(newFirstName);
        }
        if(Objects.nonNull(newLastName) && !newLastName.isEmpty())  {
            authorizedAppUser.setLastName(newLastName);
        }
        if(Objects.nonNull(userType)){
            authorizedAppUser.setUserType(userType);
        }
        if(Objects.nonNull(isActive)){
            authorizedAppUser.setActive(isActive);
        }

        authorizedAppUser = this.appUserRepository.save(authorizedAppUser);

        return AppUserMapper.mapEntityToAppUserResponseDto(authorizedAppUser);
    }
}
