package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByName(String roleName);
}
