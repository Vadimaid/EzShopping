package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_role")
public class AppRole extends BaseEntity {

    @Column(name = "role_name")
    private String name;

    public AppRole() {
    }

    public String getName() {
        return name;
    }

    public AppRole setName(String name) {
        this.name = name;
        return this;
    }
}
