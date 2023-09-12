package kg.ezshopping.ezshopping.adapter;

import kg.ezshopping.ezshopping.entity.AppUser;
import kg.ezshopping.ezshopping.mapper.InMemoryUserDetailsUserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDetailsManagerAdapter implements UserDetailsService {
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private Map<String, AppUser> appUserSpecificFieldsRegistry;

    public InMemoryUserDetailsManagerAdapter(AppUser... appUsers) {
        this.registerAppUsersSpecificFields(appUsers);
        this.inMemoryUserDetailsManager = new InMemoryUserDetailsManager(appUsers);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) this.inMemoryUserDetailsManager.loadUserByUsername(username);
        return this.restoreAppUserFromUser(user);
    }

    private void registerAppUsersSpecificFields(AppUser... appUsers) {
        this.appUserSpecificFieldsRegistry = new HashMap<>();
        for (AppUser appUser : appUsers) {
            AppUser fieldsToRegister = new AppUser();

            fieldsToRegister.setId(appUser.getId());
            fieldsToRegister.setUserType(appUser.getUserType());
            fieldsToRegister.setCreatedAt(appUser.getCreatedAt());

            this.appUserSpecificFieldsRegistry.put(appUser.getLogin(), fieldsToRegister);
        }
    }

    private AppUser restoreAppUserFromUser(User user) {
        AppUser appUser = InMemoryUserDetailsUserMapper.mapToAppUser(user);
        AppUser fieldsToRestore = this.appUserSpecificFieldsRegistry.get(appUser.getLogin());

        appUser.setId(fieldsToRestore.getId());
        appUser.setUserType(fieldsToRestore.getUserType());
        appUser.setCreatedAt(fieldsToRestore.getCreatedAt());

        return appUser;
    }
}
