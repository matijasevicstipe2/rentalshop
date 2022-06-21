package hr.matijasevic.rentalshop.vhs;

import hr.matijasevic.rentalshop.exception.NotFoundException;
import hr.matijasevic.rentalshop.rental.RentalRepository;
import hr.matijasevic.rentalshop.rental.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vhs")
@CrossOrigin(origins = "http://localhost:4200")
public class VHSController implements Serializable {

    private VHSService vhsService;
    private RentalRepository rentalRepository;

    public VHSController(VHSService vhsService, RentalRepository rentalRepository) {
        this.vhsService = vhsService;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping
    public List<VHSDTO> getAll(){

        return vhsService.findAll();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<VHSDTO> createVHS(@Valid @RequestBody final VHSCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vhsService.save(command).get());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<VHSDTO> updateVHS(@PathVariable Long id,
                                                  @Valid @RequestBody final VHSCommand command) throws NotFoundException {
        VHSDTO vhs = vhsService.findVHSByIdDTO(id)
                .orElseThrow(() -> new NotFoundException("Not found vhs with id: " + id));
        VHS _vhs = vhsService.transferCommandToEntity(command);
        _vhs.setId(vhs.getId());

        return ResponseEntity.status(HttpStatus.OK).body(vhsService.update(_vhs).get());
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteVHSById(@PathVariable Long id) throws NotFoundException {
        VHS vhs = vhsService.findVHSById(id)
                .orElseThrow(() -> new NotFoundException("Not found vhd with id: " + id));
        rentalRepository.deleteAllByVhs(vhs);
        vhsService.deleteVHSById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VHSDTO> getVHSById(@PathVariable Long id){

        return vhsService.findVHSByIdDTO(id)
                .map(it -> ResponseEntity.status(HttpStatus.OK).body(it))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }




}
