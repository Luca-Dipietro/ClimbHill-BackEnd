package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Partecipazione;
import lucadipietro.ClimbHill_BackEnd.entities.Partita;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.PartecipazioneDTO;
import lucadipietro.ClimbHill_BackEnd.payloads.PartitaDTO;
import lucadipietro.ClimbHill_BackEnd.services.PartecipazioniService;
import lucadipietro.ClimbHill_BackEnd.services.PartiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partite")
public class PartiteController {

    @Autowired
    private PartiteService partiteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority ('ADMIN') or ('ORGANIZZATORE')")
    public Partita creaPartita(@RequestBody @Validated PartitaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return partiteService.save(body);
    }
}
