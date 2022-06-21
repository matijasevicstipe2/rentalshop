package hr.matijasevic.rentalshop.security.repository;

import hr.matijasevic.rentalshop.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
