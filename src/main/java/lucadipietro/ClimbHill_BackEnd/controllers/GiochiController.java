package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Gioco;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.GiocoDTO;
import lucadipietro.ClimbHill_BackEnd.services.GiochiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/giochi")
public class GiochiController {

    @Autowired
    private GiochiService giochiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority ('ADMIN')")
    public Gioco createGioco(@RequestBody @Validated GiocoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return giochiService.save(body);
    }

    @GetMapping("/{giocoId}")
    public Gioco findById(@PathVariable UUID giocoId) {
        return giochiService.findById(giocoId);
    }

    @GetMapping("/nomeGioco/{nome}")
    public Gioco findByNome(@PathVariable String nome) {
        return giochiService.findByNome(nome);
    }

    @GetMapping
    public Page<Gioco> getAllGiochi(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "nome") String sortBy) {
        return giochiService.getGiochi(page, size, sortBy);
    }

    @PutMapping("/{giocoId}")
    @PreAuthorize("hasAuthority ('ADMIN')")
    public Gioco findByIdAndUpdate(@PathVariable UUID giocoId, @RequestBody @Validated GiocoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return giochiService.findByIdAndUpdate(giocoId, body);
    }

    @DeleteMapping("/{giocoId}")
    @PreAuthorize("hasAuthority ('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID giocoId) {
        giochiService.findByIdAndDelete(giocoId);
    }
}
