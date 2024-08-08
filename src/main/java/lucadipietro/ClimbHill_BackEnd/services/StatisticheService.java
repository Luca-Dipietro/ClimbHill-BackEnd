package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import lucadipietro.ClimbHill_BackEnd.entities.Statistica;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.StatisticaDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.StatisticheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticheService {

    @Autowired
    private StatisticheRepository statisticheRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private SquadreService squadreService;

    public Statistica save(StatisticaDTO body) {
        Utente utente = null;
        Squadra squadra = null;

        if (body.usernameUtente() != null) {
            utente = utentiService.findByUsername(body.usernameUtente());
        }

        if (body.nomeSquadra() != null) {
            squadra = squadreService.findByNome(body.nomeSquadra());
        }

        Statistica nuovaStatistica;
        if (utente != null) {
            nuovaStatistica = new Statistica(utente);
        } else if (squadra != null) {
            nuovaStatistica = new Statistica(squadra);
        } else {
            throw new BadRequestException("Devi fornire un utente o una squadra.");
        }

        return this.statisticheRepository.save(nuovaStatistica);
    }

    public Statistica findById(UUID statisticaId) {
        return this.statisticheRepository.findById(statisticaId)
                .orElseThrow(() -> new NotFoundException(statisticaId));
    }

    public Statistica findByUtenteId(UUID utenteId) {
        return this.statisticheRepository.findByUtenteId(utenteId)
                .orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Statistica findBySquadraId(UUID squadraId) {
        return this.statisticheRepository.findBySquadraId(squadraId)
                .orElseThrow(() -> new NotFoundException(squadraId));
    }

    public Statistica findByIdAndUpdate(UUID statisticaId, StatisticaDTO body) {
        Statistica found = this.findById(statisticaId);

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

        return this.statisticheRepository.save(found);
    }

    public void findByIdAndDelete(UUID statisticaId) {
        Statistica found = this.findById(statisticaId);
        this.statisticheRepository.delete(found);
    }

    public Page<Statistica> getStatistiche(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return statisticheRepository.findAll(pageable);
    }

    public void aggiornaStatisticheUtente(UUID utenteId, boolean vittoria) {
        Statistica statistica = this.findByUtenteId(utenteId);

        if (vittoria) {
            statistica.incrementaVittorie();
        }
        statistica.incrementaPartiteGiocate();

        this.statisticheRepository.save(statistica);
    }

    public void aggiornaStatisticheSquadra(UUID squadraId, boolean vittoria) {
        Statistica statistica = this.findBySquadraId(squadraId);

        if (vittoria) {
            statistica.incrementaVittorie();
        }
        statistica.incrementaPartiteGiocate();

        this.statisticheRepository.save(statistica);
    }
}
