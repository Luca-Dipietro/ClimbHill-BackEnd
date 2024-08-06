package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.*;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.PartecipazioneDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.PartecipazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartecipazioniService {

    @Autowired
    private PartecipazioniRepository partecipazioniRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private SquadreService squadreService;

    @Autowired
    private TorneiService torneiService;

    @Autowired
    private StatisticheService statisticheService;

    public Partecipazione save(PartecipazioneDTO body){
        Utente foundUtente = utentiService.findByUsername(body.usernameUtente());
        Squadra foundSquadra = squadreService.findByNome(body.nomeSquadra());
        Torneo foundTorneo = torneiService.findByNome(body.nomeTorneo());
        if(foundTorneo.getTipoTorneo().equals(TipoTorneo.TORNEO_SINGOLO)){
            Partecipazione nuovaPartecipazioneUtente = new Partecipazione(foundUtente,foundTorneo);
            return this.partecipazioniRepository.save(nuovaPartecipazioneUtente);
        }else {
            Partecipazione nuovaPartecipazioneSquadra = new Partecipazione(foundSquadra,foundTorneo);
            return this.partecipazioniRepository.save(nuovaPartecipazioneSquadra);
        }
    }

//    public Optional<Partecipazione> getListaPartecipazioni(List<Partecipazione> partecipazioni){
//        return this.partecipazioniRepository.findByPartecipazioni(partecipazioni);
//    }
}
