package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.*;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.PartitaDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.PartiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class PartiteService {

    @Autowired
    private PartiteRepository partiteRepository;

    @Autowired
    TorneiService torneiService;

    @Autowired
    UtentiService utentiService;

    @Autowired
    PartecipazioniService partecipazioniService;

    public Partita save(PartitaDTO body){
        Torneo found = torneiService.findByNome(body.nomeTorneo());
        Partita nuovaPartita = new Partita(LocalDate.parse(body.dataInizio()), LocalTime.parse(body.oraInizio()), found);
        List<Partecipazione> foundPartecipazioni = partecipazioniService.getPartecipazioni(0, Integer.MAX_VALUE, "id").stream()
                .filter(partecipazione -> partecipazione.getTorneo().getId().equals(found.getId()))
                .toList();
        if(found.getTipoTorneo().equals(TipoTorneo.TORNEO_SINGOLO)){
            List<Utente> utenti = foundPartecipazioni.stream().map(Partecipazione::getUtente).toList();
            nuovaPartita.getUtenti().addAll(utenti);
        } else {
            List<Squadra> squadre = foundPartecipazioni.stream().map(Partecipazione::getSquadra).toList();
            nuovaPartita.getSquadre().addAll(squadre);
        }

        return this.partiteRepository.save(nuovaPartita);
    }

    public Page<Partita> getPartite(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return partiteRepository.findAll(pageable);
    }

    public Partita findById(UUID partitaId) {
        return this.partiteRepository.findById(partitaId)
                .orElseThrow(() -> new NotFoundException(partitaId));
    }

    public Partita findByIdAndUpdate(UUID partitaId, PartitaDTO body) {
        Partita found = this.findById(partitaId);
        Torneo torneo = torneiService.findByNome(body.nomeTorneo());

        found.setDataInizio(LocalDate.parse(body.dataInizio()));
        found.setOraInizio(LocalTime.parse(body.oraInizio()));
        found.setTorneo(torneo);

        List<Partecipazione> foundPartecipazioni = partecipazioniService.getPartecipazioni(0, Integer.MAX_VALUE, "id").stream()
                .filter(partecipazione -> partecipazione.getTorneo().getId().equals(torneo.getId()))
                .toList();

        if (torneo.getTipoTorneo().equals(TipoTorneo.TORNEO_SINGOLO)) {
            List<Utente> utenti = foundPartecipazioni.stream().map(Partecipazione::getUtente).toList();
            found.getUtenti().clear();
            found.getUtenti().addAll(utenti);
        } else {
            List<Squadra> squadre = foundPartecipazioni.stream().map(Partecipazione::getSquadra).toList();
            found.getSquadre().clear();
            found.getSquadre().addAll(squadre);
        }

        return this.partiteRepository.save(found);
    }

    public void findByIdAndDelete(UUID partitaId) {
        Partita found = this.findById(partitaId);
        this.partiteRepository.delete(found);
    }
}
