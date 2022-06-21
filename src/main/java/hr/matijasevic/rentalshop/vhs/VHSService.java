package hr.matijasevic.rentalshop.vhs;

import hr.matijasevic.rentalshop.exception.InvalidRentalPeriodException;
import hr.matijasevic.rentalshop.exception.NotFoundException;
import hr.matijasevic.rentalshop.exception.VHSNotAvailableException;
import hr.matijasevic.rentalshop.rental.Rental;
import hr.matijasevic.rentalshop.rental.RentalCommand;
import hr.matijasevic.rentalshop.rental.RentalDTO;

import java.util.List;
import java.util.Optional;

public interface VHSService {
    List<VHSDTO> findAll();
    Optional<VHSDTO> findVHSByIdDTO(Long id);
    Optional<VHS> findVHSById(Long id);
    Optional<VHSDTO> save(VHSCommand vhsCommand);
    VHS transferCommandToEntity(VHSCommand command) ;
    Optional<VHSDTO> update(VHS vhs);
    void deleteVHSById(Long id);
}
