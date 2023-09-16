package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    List<ItemCategory> findAllByIsActiveAndParentIsNull(Boolean active);
    List<ItemCategory> findAllByIsActiveAndParentId(Boolean active, Long parentId);

}
