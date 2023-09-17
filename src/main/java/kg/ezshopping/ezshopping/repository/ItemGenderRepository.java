package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.ItemGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemGenderRepository extends JpaRepository<ItemGender, Long> {
}
