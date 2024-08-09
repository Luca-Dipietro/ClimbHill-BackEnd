package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.SquadraDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.SquadreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SquadreService {

    @Autowired
    private SquadreRepository squadreRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private RuoliService ruoliService;

    public Squadra save(SquadraDTO body, UUID utenteId){
        Utente creatore = utentiService.findById(utenteId);

        squadreRepository.findByNome(body.nome()).ifPresent(
                squadra -> {
                    throw new BadRequestException("La squadra con il nome " + body.nome() + " è già presente!");
                }
        );
        Squadra nuovaSquadra = new Squadra(body.nome());
        nuovaSquadra.getMembri().add(creatore);
        Ruolo found = ruoliService.findByRuolo(TipoRuolo.CAPO_SQUADRA);
        creatore.getRuoli().add(found);
        utentiService.update(creatore);
        return squadreRepository.save(nuovaSquadra);
    }

    public Squadra findById(UUID squadraId) {
        return this.squadreRepository.findById(squadraId).orElseThrow(() -> new NotFoundException(squadraId));
    }

    public Squadra findByNome(String nome){
        return this.squadreRepository.findByNome(nome).orElseThrow(() -> new NotFoundException(nome));
    }

    public Squadra findByIdAndUpdate(UUID squadraId, SquadraDTO body) {
        Squadra found = this.findById(squadraId);
        found.setNome(body.nome());
        return this.squadreRepository.save(found);
    }

    public void findByIdAndDelete(UUID squadraId) {
        Squadra found = this.findById(squadraId);
        this.squadreRepository.delete(found);
    }

    public Squadra addMembro(UUID squadraId, UUID utenteId) {
        Squadra squadra = squadreRepository.findById(squadraId).orElseThrow(() -> new NotFoundException(squadraId));

        Utente utente = utentiService.findById(utenteId);

        if (squadra.getMembri().contains(utente)) {
            throw new BadRequestException("L'utente è già un membro della squadra!");
        }

        squadra.getMembri().add(utente);
        utente.getSquadre().add(squadra);

        squadreRepository.save(squadra);
        utentiService.update(utente);
        return squadra;
    }

    public Squadra removeMembro(UUID squadraId, UUID utenteId) {
        Squadra squadra = squadreRepository.findById(squadraId).orElseThrow(() -> new NotFoundException(squadraId));

        Utente utente = utentiService.findById(utenteId);

        if (!squadra.getMembri().contains(utente)) {
            throw new BadRequestException("L'utente non è un membro della squadra!");
        }

        squadra.getMembri().remove(utente);
        utente.getSquadre().remove(squadra);

        this.squadreRepository.save(squadra);
        this.utentiService.update(utente);
        return squadra;
    }
}
