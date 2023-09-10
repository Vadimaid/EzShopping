package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.repository.AppUserRepository;
import kg.ezshopping.ezshopping.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsServiceImpl(
            AppUserRepository appUserRepository
    ) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        if(Objects.isNull(username) || username.isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть null или пустым!");
        }

        Optional<AppUser> appUserOptional = this.appUserRepository.getAppUserByLogin(username);
        if(appUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователя с указанным логином не найдено в системе!");
        }
        return appUserOptional.get();
    }
}
