package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.ItemColour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemColourRepository extends JpaRepository<ItemColour, Long> {

    List<ItemColour> findAllByIdIn(List<Long> ids);
}
