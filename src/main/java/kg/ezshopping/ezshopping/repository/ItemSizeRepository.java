package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.ItemSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSizeRepository extends JpaRepository<ItemSize, Long> {

    List<ItemSize> findAllByIdIn(List<Long> ids);
}
