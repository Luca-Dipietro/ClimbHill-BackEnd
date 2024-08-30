package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Partecipazione;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.PartecipazioneDTO;
import lucadipietro.ClimbHill_BackEnd.services.PartecipazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partecipazioni")
public class PartecipazioniController {

    @Autowired
    private PartecipazioniService partecipazioniService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Partecipazione creaPartecipazione(@RequestBody @Validated PartecipazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return partecipazioniService.save(body);
    }

    @GetMapping
    public Page<Partecipazione> getPartecipazioni(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        return partecipazioniService.getPartecipazioni(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/torneo/{nomeTorneo}")
    public List<Partecipazione> getPartecipazioniPerTorneo(@PathVariable String nomeTorneo) {
        return partecipazioniService.findByTorneoNome(nomeTorneo);
    }
}
