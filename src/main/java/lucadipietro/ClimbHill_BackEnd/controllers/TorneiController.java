package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Torneo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.TorneoDTO;
import lucadipietro.ClimbHill_BackEnd.services.TorneiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/search")
    public Page<Torneo> searchTornei(@RequestParam(required = false) String nome,
                                     @RequestParam(required = false) String tipoTorneo,
                                     @RequestParam(required = false) String dataInizioIscrizione,
                                     @RequestParam(required = false) String dataFineIscrizione,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "nome") String sortBy) {
        TipoTorneo tipo = null;
        if(tipoTorneo != null){
            tipo = TipoTorneo.getTipoTorneo(tipoTorneo);
        }
        LocalDate startDate = dataInizioIscrizione != null ? LocalDate.parse(dataInizioIscrizione) : null;
        LocalDate endDate = dataFineIscrizione != null ? LocalDate.parse(dataFineIscrizione) : null;
        return torneiService.searchTornei(nome, tipo, startDate, endDate, page, size, sortBy);
    }
}