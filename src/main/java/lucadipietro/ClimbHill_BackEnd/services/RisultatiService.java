package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Partita;
import lucadipietro.ClimbHill_BackEnd.entities.Risultato;
import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.RisultatoDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.RisultatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RisultatiService {

    @Autowired
    private RisultatiRepository risultatiRepository;

    @Autowired
    private PartiteService partiteService;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private SquadreService squadreService;

    public Risultato save(RisultatoDTO body, UUID partitaId) {
        Partita partita = partiteService.findById(partitaId);

        Utente utente = null;
        Squadra squadra = null;

        if (body.usernameUtente() != null) {
            utente = utentiService.findByUsername(body.usernameUtente());
        }

        if (body.nomeSquadra() != null) {
            squadra = squadreService.findByNome(body.nomeSquadra());
        }

        Risultato nuovoRisultato = new Risultato(body.punteggio(), partita, utente, squadra);
        return this.risultatiRepository.save(nuovoRisultato);
    }

    public Risultato findById(UUID risultatoId) {
        return this.risultatiRepository.findById(risultatoId)
                .orElseThrow(() -> new NotFoundException(risultatoId));
    }

    public Risultato findByIdAndUpdate(UUID risultatoId, RisultatoDTO body) {
        Risultato found = this.findById(risultatoId);

        found.setPunteggio(body.punteggio());

        if (body.usernameUtente() != null) {
            Utente utente = utentiService.findByUsername(body.usernameUtente());
            found.setUtente(utente);
            found.setSquadra(null);
        }

        if (body.nomeSquadra() != null) {
            Squadra squadra = squadreService.findByNome(body.nomeSquadra());
            found.setSquadra(squadra);
            found.setUtente(null);
        }

        return this.risultatiRepository.save(found);
    }

    public void findByIdAndDelete(UUID risultatoId) {
        Risultato found = this.findById(risultatoId);
        this.risultatiRepository.delete(found);
    }

    public Page<Risultato> getRisultati(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return risultatiRepository.findAll(pageable);
    }
}