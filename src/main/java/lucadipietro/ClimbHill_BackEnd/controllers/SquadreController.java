package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.SquadraDTO;
import lucadipietro.ClimbHill_BackEnd.services.SquadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/squadre")
public class SquadreController {

    @Autowired
    private SquadreService squadreService;

    @PostMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Squadra createSquadra(@PathVariable UUID utenteId, @RequestBody @Validated SquadraDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return squadreService.save(body, utenteId);
    }

    @GetMapping("/{squadraId}")
    public Squadra findById(@PathVariable UUID squadraId) {
        return squadreService.findById(squadraId);
    }

    @GetMapping("/nome")
    public Squadra findByNome(@RequestParam String nome) {
        return squadreService.findByNome(nome);
    }

    @PutMapping("/{squadraId}")
    public Squadra updateSquadra(@PathVariable UUID squadraId, @RequestBody @Validated SquadraDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return squadreService.findByIdAndUpdate(squadraId, body);
    }

    @DeleteMapping("/{squadraId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSquadra(@PathVariable UUID squadraId) {
        squadreService.findByIdAndDelete(squadraId);
    }

    @PostMapping("/{squadraId}/membri/{utenteId}")
    public Squadra addMembro(@PathVariable UUID squadraId, @PathVariable UUID utenteId) {
        return squadreService.addMembro(squadraId, utenteId);
    }

    @DeleteMapping("/{squadraId}/membri/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMembro(@PathVariable UUID squadraId, @PathVariable UUID utenteId) {
        squadreService.removeMembro(squadraId, utenteId);
    }
}