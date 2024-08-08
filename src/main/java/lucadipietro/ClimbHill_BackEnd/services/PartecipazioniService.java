package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.*;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.payloads.PartecipazioneDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.PartecipazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Torneo foundTorneo = torneiService.findByNome(body.nomeTorneo());
        if(foundTorneo.getTipoTorneo().equals(TipoTorneo.TORNEO_SINGOLO)){
            Utente foundUtente = utentiService.findByUsername(body.usernameUtente());
            Partecipazione nuovaPartecipazioneUtente = new Partecipazione(foundUtente,foundTorneo);
            return this.partecipazioniRepository.save(nuovaPartecipazioneUtente);
        }else {
            Squadra foundSquadra = squadreService.findByNome(body.nomeSquadra());
            Partecipazione nuovaPartecipazioneSquadra = new Partecipazione(foundSquadra,foundTorneo);
            return this.partecipazioniRepository.save(nuovaPartecipazioneSquadra);
        }
    }

    public Page<Partecipazione> getPartecipazioni(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return partecipazioniRepository.findAll(pageable);
    }
}
