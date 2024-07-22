package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.exceptions.UnauthorizedException;
import lucadipietro.ClimbHill_BackEnd.payloads.UtenteLoginDTO;
import lucadipietro.ClimbHill_BackEnd.security.JWTTokenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTokenConfiguration jwtTokenConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndGenerateToken(UtenteLoginDTO body) {
        Utente nuovoUtente = this.utentiService.findByEmail(body.email());
        if (passwordEncoder.matches(body.password(), nuovoUtente.getPassword())) {
            return jwtTokenConfiguration.createToken(nuovoUtente);
        } else {
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
