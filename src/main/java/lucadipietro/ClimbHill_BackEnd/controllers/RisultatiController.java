package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Risultato;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.RisultatoDTO;
import lucadipietro.ClimbHill_BackEnd.services.RisultatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/risultati")
public class RisultatiController {

    @Autowired
    private RisultatiService risultatiService;

    @PostMapping("/{partitaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority ('ADMIN') or ('ORGANIZZATORE')")
    public Risultato creaRisultato(@RequestBody @Validated RisultatoDTO body,
                                   @PathVariable UUID partitaId,BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return risultatiService.save(body, partitaId);
    }

    @GetMapping("/{risultatoId}")
    public Risultato findById(@PathVariable UUID risultatoId) {
        return risultatiService.findById(risultatoId);
    }

    @PutMapping("/{risultatoId}")
    @PreAuthorize("hasAuthority ('ADMIN') or ('ORGANIZZATORE')")
    public Risultato findByIdAndUpdate(@PathVariable UUID risultatoId,
                                       @RequestBody @Validated RisultatoDTO body,
                                       BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return risultatiService.findByIdAndUpdate(risultatoId, body);
    }

    @DeleteMapping("/{risultatoId}")
    @PreAuthorize("hasAuthority ('ADMIN') or ('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID risultatoId) {
        risultatiService.findByIdAndDelete(risultatoId);
    }

    @GetMapping
    public Page<Risultato> getAllRisultati(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return risultatiService.getRisultati(page, size, sortBy);
    }
}
