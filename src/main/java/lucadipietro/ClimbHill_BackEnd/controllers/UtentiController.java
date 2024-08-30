package lucadipietro.ClimbHill_BackEnd.controllers;

import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.payloads.UtenteDTO;
import lucadipietro.ClimbHill_BackEnd.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), body);
    }

    @GetMapping
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.getUtenti(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    public Utente findById(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return this.utentiService.findByIdAndUpdate(utenteId, body);
        }
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    @PatchMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente uploadAvatarFromAdmin(@PathVariable UUID utenteId, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.utentiService.uploadImage(utenteId, image);
    }

    @PatchMapping("/me/avatar")
    public Utente uploadAvatar(@AuthenticationPrincipal Utente currentAuthenticatedUtente,@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.utentiService.uploadImage(currentAuthenticatedUtente.getId(), image);
    }
}
