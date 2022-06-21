package hr.matijasevic.rentalshop.rental;

import hr.matijasevic.rentalshop.exception.InvalidRentalPeriodException;
import hr.matijasevic.rentalshop.exception.NotFoundException;
import hr.matijasevic.rentalshop.exception.VHSNotAvailableException;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<RentalDTO> findAll();
    Optional<RentalDTO> save(RentalCommand command) throws NotFoundException, VHSNotAvailableException, InvalidRentalPeriodException;
    Optional<RentalDTO> findRentalById(Long id);
    Rental transferCommandToEntity(RentalCommand command,boolean newRental) throws NotFoundException,
            VHSNotAvailableException, InvalidRentalPeriodException;
    Optional<RentalDTO> update(Rental rental);
    void deleteRentalById(Long id);
}
