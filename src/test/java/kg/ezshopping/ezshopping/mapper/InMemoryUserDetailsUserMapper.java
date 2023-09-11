package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.entity.AppRole;
import kg.ezshopping.ezshopping.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDetailsUserMapper {
    public static AppUser mapToAppUser(User user) {
        AppUser appUser = new AppUser();

        appUser.setLogin(user.getUsername());
        appUser.setPassword(user.getPassword());
        appUser.setActive(user.isEnabled());

        List<AppRole> appRoleList = new ArrayList<>();
        for (GrantedAuthority authority: user.getAuthorities()) {
            appRoleList.add((AppRole) authority);
        }

        appUser.setUserRolesList(appRoleList);
        return appUser;
    }
}
