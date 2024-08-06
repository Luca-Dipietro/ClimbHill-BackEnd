package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.*;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.payloads.PartitaDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.PartiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

//    public Partita save(PartitaDTO body){
//        Torneo found = torneiService.findByNome(body.nomeTorneo());
//        Partita nuovaPartita = new Partita(LocalDate.parse(body.dataInizio()), LocalTime.parse(body.oraInizio()), found);
//        Optional<Partecipazione> foundPartecipazioni = partecipazioniService.getListaPartecipazioni(found.getPartecipazioni());
//        List<Utente> utenti = foundPartecipazioni.stream().map(Partecipazione::getUtente).toList();
//        List<Squadra> squadre = foundPartecipazioni.stream().map(Partecipazione::getSquadra).toList();
//
//        if(found.getTipoTorneo().equals(TipoTorneo.TORNEO_SINGOLO)){
//            nuovaPartita.getUtenti().addAll(utenti);
//        } else {
//            nuovaPartita.getSquadre().addAll(squadre);
//        }
//
//        return this.partiteRepository.save(nuovaPartita);
//    }
}
