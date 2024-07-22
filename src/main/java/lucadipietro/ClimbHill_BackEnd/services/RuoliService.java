package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.repositories.RuoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoliService {
    @Autowired
    private RuoliRepository ruoliRepository;

    public Ruolo findByRole(TipoRuolo ruolo) {
        return ruoliRepository.findByRole(ruolo).orElseThrow(() -> new NotFoundException("Ruolo " + ruolo + " non trovato!"));
    }
}
