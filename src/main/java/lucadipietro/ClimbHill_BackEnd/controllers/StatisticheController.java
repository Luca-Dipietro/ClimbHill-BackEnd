package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Statistica;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.StatisticaDTO;
import lucadipietro.ClimbHill_BackEnd.services.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/statistiche")
public class StatisticheController {

    @Autowired
    private StatisticheService statisticheService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Statistica creaStatistica(@RequestBody @Validated StatisticaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return statisticheService.save(body);
    }

    @GetMapping("/{statisticaId}")
    public Statistica findById(@PathVariable UUID statisticaId) {
        return statisticheService.findById(statisticaId);
    }

    @PutMapping("/{statisticaId}")
    @PreAuthorize("hasAuthority ('ADMIN')")
    public Statistica findByIdAndUpdate(@PathVariable UUID statisticaId,
                                        @RequestBody @Validated StatisticaDTO body,
                                        BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return statisticheService.findByIdAndUpdate(statisticaId, body);
    }

    @DeleteMapping("/{statisticaId}")
    @PreAuthorize("hasAuthority ('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID statisticaId) {
        statisticheService.findByIdAndDelete(statisticaId);
    }

    @GetMapping
    public Page<Statistica> getAllStatistiche(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        return statisticheService.getStatistiche(page, size, sortBy);
    }

    @GetMapping("/utente/{utenteId}")
    public Statistica findByUtenteId(@PathVariable UUID utenteId) {
        return statisticheService.findByUtenteId(utenteId);
    }

    @GetMapping("/squadra/{squadraId}")
    public Statistica findBySquadraId(@PathVariable UUID squadraId) {
        return statisticheService.findBySquadraId(squadraId);
    }
}
