package hr.matijasevic.rentalshop.security.service;

import hr.matijasevic.rentalshop.security.command.LoginCommand;
import hr.matijasevic.rentalshop.security.dto.LoginDTO;

import java.util.Optional;

public interface AuthenticationService {

    Optional<LoginDTO> login(LoginCommand command);

}