package kg.ezshopping.ezshopping.repository;

import kg.ezshopping.ezshopping.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository
        extends JpaRepository<AppUser, Long>,
        QuerydslPredicateExecutor<AppUser>
{
    Optional<AppUser> getAppUserByLogin(String login);
}
