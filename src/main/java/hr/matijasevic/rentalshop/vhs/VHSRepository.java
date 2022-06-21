package hr.matijasevic.rentalshop.vhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("vhsRepository")
public interface VHSRepository extends JpaRepository<VHS,Long> {

    List<VHS> findAll();
    Optional<VHS> findById(Long id);
}
