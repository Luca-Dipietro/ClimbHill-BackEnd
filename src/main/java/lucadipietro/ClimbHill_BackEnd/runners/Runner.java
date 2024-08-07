package lucadipietro.ClimbHill_BackEnd.runners;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import lucadipietro.ClimbHill_BackEnd.repositories.RuoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    RuoliRepository ruoliRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (!ruoliRepository.existsByRuolo(TipoRuolo.ADMIN)) {
            ruoliRepository.save(new Ruolo(TipoRuolo.ADMIN));
        }

        if (!ruoliRepository.existsByRuolo(TipoRuolo.UTENTE)) {
            ruoliRepository.save(new Ruolo(TipoRuolo.UTENTE));
        }

        if (!ruoliRepository.existsByRuolo(TipoRuolo.CAPO_SQUADRA)) {
            ruoliRepository.save(new Ruolo(TipoRuolo.CAPO_SQUADRA));
        }

    }
}
