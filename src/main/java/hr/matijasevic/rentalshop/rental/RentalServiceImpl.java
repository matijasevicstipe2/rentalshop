package hr.matijasevic.rentalshop.rental;

import hr.matijasevic.rentalshop.exception.InvalidRentalPeriodException;
import hr.matijasevic.rentalshop.exception.NotFoundException;
import hr.matijasevic.rentalshop.exception.VHSNotAvailableException;
import hr.matijasevic.rentalshop.security.domain.User;
import hr.matijasevic.rentalshop.security.repository.UserRepository;
import hr.matijasevic.rentalshop.vhs.VHS;
import hr.matijasevic.rentalshop.vhs.VHSRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements Serializable,RentalService {

    private RentalRepository rentalRepository;
    private VHSRepository vhsRepository;
    private UserRepository userRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, VHSRepository vhsRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.vhsRepository = vhsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RentalDTO> findAll() {
        return rentalRepository.findAll().stream()
                .map(this::mapRentalToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<RentalDTO> save(RentalCommand command) throws NotFoundException,
            VHSNotAvailableException, InvalidRentalPeriodException {

        return Optional.of(rentalRepository.save(transferCommandToEntity(command,true)))
                .map(this::mapRentalToDTO);
    }

    @Override
    public Optional<RentalDTO> findRentalById(Long id) {
        return rentalRepository.findById(id).map(this::mapRentalToDTO);
    }

    @Override
    public Rental transferCommandToEntity(RentalCommand command, boolean newRental) throws NotFoundException, VHSNotAvailableException, InvalidRentalPeriodException {

        Optional<VHS> vhs = vhsRepository.findById(command.getVhsId());
        Optional<User> user = userRepository.findById(command.getUserId());
        //handle wrong ids
        if(user.isEmpty()){
            throw new NotFoundException("User not found with id: " + command.getUserId());
        }else if(vhs.isEmpty()){
            throw new NotFoundException("VHS not found with id: " + command.getVhsId());
        }else{
            //check if vhs is available
            List<Rental> rentals = new ArrayList<>();
            if(newRental && (LocalDate.now().isBefore(command.getReturnDate().toLocalDate())  ||
                    LocalDate.now().isEqual(command.getReturnDate().toLocalDate()))) {
                rentals = rentalRepository
                        .findAllByStatusAndVhs(Status.OUT, vhs.get());
                rentals.addAll(rentalRepository.findAllByStatusAndVhs(Status.LATE, vhs.get()));
            }
            if(!(rentals.isEmpty())){
                throw new VHSNotAvailableException("This VHS is currently not available!");
            }else{
                //ako je OUT mora bit today before returnDate,
                // a ako je today after returnDate mora biti ili RETURNED ili LATE
                // provjeri jeli rental date prije
                if(command.getRentalDate().toLocalDate().isAfter(command.getReturnDate().toLocalDate()) ||
                        command.getRentalDate().toLocalDate().isEqual(command.getReturnDate().toLocalDate())){

                    throw new InvalidRentalPeriodException("Rental date must be at least 1 day before return date!");
                }
                //ako imamo status OUT to znaci da je rental period u tijeku, znaci
                // ne smije biti prije rental date niti poslije return date
                if(command.getStatus().getStatus().equals("OUT")
                        && (LocalDate.now().isAfter(command.getReturnDate().toLocalDate()) ||
                        LocalDate.now().isBefore(command.getRentalDate().toLocalDate()))){
                    throw new InvalidRentalPeriodException("[CHANGE STATUS OR RENTAL PERIOD] If rental status is OUT " +
                            "then today cannot be after return date or before rental date!");
                }
                /* ako je status late to znaci da today ne smije biti prije return date*/
                if(command.getStatus().getStatus().equals("LATE")
                        && (LocalDate.now().isBefore(command.getReturnDate().toLocalDate()) ||
                        LocalDate.now().isBefore(command.getRentalDate().toLocalDate()))){
                    throw new InvalidRentalPeriodException("[CHANGE STATUS OR RENTAL PERIOD] If rental status is LATE " +
                            "then today cannot be before return date or before rental date");
                }
                //ako je status returned
                if(command.getStatus().getStatus().equals("RETURNED")
                        && LocalDate.now().isBefore(command.getRentalDate().toLocalDate())){
                    throw new InvalidRentalPeriodException("[CHANGE STATUS OR RENTAL PERIOD] If rental status is RETURNED " +
                            "then today cannot be before rental date");
                }

                Duration duration = Duration.between(command.getRentalDate().toLocalDate().atStartOfDay(),
                        command.getReturnDate().toLocalDate().atStartOfDay());
                Integer days = (int) duration.toDays();
                Integer paid = vhs.get().getPrice() * days;

                return new Rental(command.getRentalDate(),command.getReturnDate(),
                        paid,command.getStatus(),vhs.get(),user.get());
            }
        }
    }

    @Override
    public Optional<RentalDTO> update(Rental rental) {
        return Optional.of(rentalRepository.save(rental)).map(this::mapRentalToDTO);
    }

    @Override
    public void deleteRentalById(Long id) {
        rentalRepository.deleteById(id);
    }

    private RentalDTO mapRentalToDTO(Rental rental){
        Integer lateFee = 0;
        if(rental.getStatus().equals(Status.LATE)){
            lateFee = calculateLateFee(rental);
        }

        return new RentalDTO(rental.getId(),rental.getUser().getId(),
                rental.getVhs().getId(),rental.getRentalDate(),
                rental.getReturnDate(),rental.getPaid(),rental.getStatus(),lateFee);
    }

    private Integer calculateLateFee(Rental rental){
        Duration duration = Duration.between(rental.getReturnDate().toLocalDate().atStartOfDay(),LocalDate.now().atStartOfDay());
        Integer days = (int) duration.toDays();
        Integer fee = 5;
        return  fee * days;
    }

}
