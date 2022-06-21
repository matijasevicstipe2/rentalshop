package hr.matijasevic.rentalshop.security.service;

import hr.matijasevic.rentalshop.security.domain.User;

public interface JwtService {

    boolean authenticate(String token);

    String createJwt(User user);

}
