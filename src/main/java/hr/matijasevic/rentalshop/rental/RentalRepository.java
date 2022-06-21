package hr.matijasevic.rentalshop.rental;

import hr.matijasevic.rentalshop.vhs.VHS;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface RentalRepository extends JpaRepository<Rental,Long> {
    List<Rental> findAll();
    Optional<Rental> findById(Long id);
    List<Rental> findAllByStatusAndVhs(Status status,VHS vhs);
    List<Rental> deleteAllByVhs(VHS vhs);
}
