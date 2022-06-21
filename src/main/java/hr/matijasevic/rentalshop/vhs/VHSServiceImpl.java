package hr.matijasevic.rentalshop.vhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VHSServiceImpl implements Serializable,VHSService{

    @Qualifier("vhsRepository")
    private VHSRepository vhsRepository;

    public VHSServiceImpl(VHSRepository vhsRepository) {
        this.vhsRepository = vhsRepository;
    }

    @Override
    public List<VHSDTO> findAll() {
        return vhsRepository.findAll().stream().map(this::mapVHSToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<VHSDTO> findVHSByIdDTO(Long id) {
        return vhsRepository.findById(id).map(this::mapVHSToDTO);
    }

    @Override
    public Optional<VHS> findVHSById(Long id) {
        return vhsRepository.findById(id);
    }

    @Override
    public Optional<VHSDTO> save(VHSCommand vhsCommand) {
        return Optional.of(vhsRepository.save(transferCommandToEntity(vhsCommand)))
                .map(this::mapVHSToDTO);
    }

    @Override
    public VHS transferCommandToEntity(VHSCommand command) {
        return new VHS(command.getName(),command.getInfo(),command.getReleaseYear(),
                command.getRating(),command.getRuntime(),command.getScore(),command.getPrice());
    }

    @Override
    public Optional<VHSDTO> update(VHS vhs) {
        return Optional.of(vhsRepository.save(vhs)).map(this::mapVHSToDTO);
    }

    @Override
    public void deleteVHSById(Long id) {
        vhsRepository.deleteById(id);
    }

    private VHSDTO mapVHSToDTO(VHS vhs){
        return new VHSDTO(vhs.getId(), vhs.getName(), vhs.getInfo(),
                vhs.getReleaseYear(),vhs.getRating(), vhs.getRuntime(), vhs.getScore(), vhs.getPrice());
    }
}
