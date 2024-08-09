package lucadipietro.ClimbHill_BackEnd.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.entities.Statistica;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.StatisticaDTO;
import lucadipietro.ClimbHill_BackEnd.payloads.UtenteDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private Cloudinary cloudinary;

    public Utente save(UtenteDTO body) {
        utentiRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("L'utente con l'email: " + body.email() + " è già presente!");
        });
        utentiRepository.findByUsername(body.username()).ifPresent(
                utente -> {
                    throw new BadRequestException("L'utente " + body.username() + " è già presente!");
        });
        Ruolo found = ruoliService.findByRuolo(TipoRuolo.UTENTE);
        Utente nuovoUtente = new Utente(body.username(), body.email(), passwordEncoder.encode(body.password()), body.nome(), body.cognome());
        if (!nuovoUtente.getRuoli().contains(found)) {
            nuovoUtente.setRuoli(List.of(found));
        } else {
            throw new BadRequestException("L'utente " + body.nome() + " possiede già questo ruolo");
        }
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.utentiRepository.save(nuovoUtente);
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public Utente findByUsername(String username){
        return this.utentiRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Utente con l'username " + username + " non trovato!"));
    }

    public Page<Utente> getUtenti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utentiRepository.findAll(pageable);
    }

    public Utente update(Utente utente) {
        return utentiRepository.save(utente);
    }

    public Utente findByIdAndUpdate(UUID utenteId, UtenteDTO body) {
        Utente found = this.findById(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(passwordEncoder.encode(body.password()));
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.utentiRepository.save(found);
    }

    public void findByIdAndDelete(UUID utenteId) {
        Utente found = this.findById(utenteId);
        this.utentiRepository.delete(found);
    }

    public Utente uploadImage(UUID utenteId, MultipartFile file) throws IOException {
        Utente found = this.findById(utenteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        return this.utentiRepository.save(found);
    }
}
