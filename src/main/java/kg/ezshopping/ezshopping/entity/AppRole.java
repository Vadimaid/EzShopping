package kg.ezshopping.ezshopping.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppRole appRole = (AppRole) o;
        return Objects.equals(name, appRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
