package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>,
        QuerydslPredicateExecutor<Store> {
    Optional<Store> getStoreById(Long id);
    Optional<Store> getStoreByFullName(String fullName);

}
