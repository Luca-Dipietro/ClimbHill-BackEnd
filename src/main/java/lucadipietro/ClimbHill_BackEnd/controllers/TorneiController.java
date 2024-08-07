package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Torneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.TorneoDTO;
import lucadipietro.ClimbHill_BackEnd.services.TorneiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tornei")
public class TorneiController {

    @Autowired
    private TorneiService torneiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Torneo createTorneo(@RequestBody @Validated TorneoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return torneiService.save(body);
    }

    @GetMapping("/{torneoId}")
    public Torneo findById(@PathVariable UUID torneoId) {
        return torneiService.findById(torneoId);
    }

    @GetMapping("/nomeTorneo/{nome}")
    public Torneo findByNome(@PathVariable String nome) {
        return torneiService.findByNome(nome);
    }

    @GetMapping
    public Page<Torneo> getAllTornei(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return torneiService.getTornei(page, size, sortBy);
    }

    @PutMapping("/{torneoId}")
    public Torneo updateTorneo(@PathVariable UUID torneoId, @RequestBody @Validated TorneoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return torneiService.findByIdAndUpdate(torneoId, body);
    }

    @DeleteMapping("/{torneoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTorneo(@PathVariable UUID torneoId) {
        torneiService.findByIdAndDelete(torneoId);
    }
}