package kg.ezshopping.ezshopping.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_role")
public class AppRole extends BaseEntity implements GrantedAuthority {

    @Column(name = "role_name")
    private String name;

    @ManyToMany(mappedBy = "userRolesList", cascade = CascadeType.MERGE)
    private List<AppUser> appUserList;

    public AppRole() {
    }

    public String getName() {
        return name;
    }

    public AppRole setName(String name) {
        this.name = name;
        return this;
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public AppRole setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
        return this;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }
}
