package hr.matijasevic.rentalshop.security.service;

import hr.matijasevic.rentalshop.security.command.LoginCommand;
import hr.matijasevic.rentalshop.security.domain.User;
import hr.matijasevic.rentalshop.security.dto.LoginDTO;
import hr.matijasevic.rentalshop.security.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<LoginDTO> login(LoginCommand command) {
        Optional<User> user = userRepository.findByUsername(command.getUsername());

        if (user.isEmpty() || !isMatchingPassword(command.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.of(
                new LoginDTO(jwtService.createJwt(user.get()))
        );

    }

    private boolean isMatchingPassword(String rawPassword, String encryptedPassword) {
        if(new BCryptPasswordEncoder().matches(rawPassword,encryptedPassword)){
            return true;
        }else{
            return false;
        }

    }
}
