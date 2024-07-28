package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.RuoloDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.RuoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoliService {
    @Autowired
    private RuoliRepository ruoliRepository;

    public Ruolo save(RuoloDTO body){
        Ruolo nuovoRuolo = new Ruolo(TipoRuolo.valueOf(body.ruolo().toUpperCase()));
        return this.ruoliRepository.save(nuovoRuolo);
    }

    public Ruolo findByRuolo(TipoRuolo ruolo) {
        return ruoliRepository.findByRuolo(ruolo).orElseThrow(() -> new NotFoundException("Ruolo " + ruolo + " non trovato!"));
    }
}
