package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.UtenteDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private RuoliService ruoliService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente save(UtenteDTO body) {
        utentiRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("L'utente con l'email: " + body.email() + " esiste già!");
        });
        utentiRepository.findByUsername(body.username()).ifPresent(
                utente -> {
                    throw new BadRequestException("L'utente " + body.username() + " esiste già!");
        });
        Ruolo found = ruoliService.findByRole(TipoRuolo.UTENTE);
        Utente nuovoUtente = new Utente(body.username(), body.email(), passwordEncoder.encode(body.password()), body.nome(), body.cognome());
        if (!nuovoUtente.getRuoli().contains(found)) {
            nuovoUtente.setRuoli(Set.of(found));
        } else {
            throw new BadRequestException("L'utente " + body.nome() + " possiede già questo ruolo");
        }
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return utentiRepository.save(nuovoUtente);
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
