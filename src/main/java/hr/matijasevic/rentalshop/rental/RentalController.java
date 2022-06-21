package hr.matijasevic.rentalshop.rental;

import hr.matijasevic.rentalshop.exception.InvalidRentalPeriodException;
import hr.matijasevic.rentalshop.exception.NotFoundException;
import hr.matijasevic.rentalshop.exception.VHSNotAvailableException;
import hr.matijasevic.rentalshop.vhs.VHS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("api/rental")
@CrossOrigin(origins = "http://localhost:4200")
@Secured("ROLE_ADMIN")
public class RentalController implements Serializable {

    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<RentalDTO> getAllRentals(){
        return rentalService.findAll();
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@Valid @RequestBody final RentalCommand command)
            throws NotFoundException, VHSNotAvailableException, InvalidRentalPeriodException {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.save(command).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id,
                                               @Valid @RequestBody final RentalCommand command)
            throws NotFoundException, VHSNotAvailableException, InvalidRentalPeriodException {
        RentalDTO rental = rentalService.findRentalById(id)
                .orElseThrow(() -> new NotFoundException("Not found rental with id: " + id));
        Rental _rental = rentalService.transferCommandToEntity(command,false);
        _rental.setId(rental.getId());

        return ResponseEntity.status(HttpStatus.OK).body(rentalService.update(_rental).get());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRentalById(@PathVariable Long id) throws NotFoundException {
        RentalDTO rental = rentalService.findRentalById(id)
                .orElseThrow(() -> new NotFoundException("Not found rental with id: " + id));
        rentalService.deleteRentalById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id){
        return rentalService.findRentalById(id)
                .map(it -> ResponseEntity.status(HttpStatus.OK).body(it))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
