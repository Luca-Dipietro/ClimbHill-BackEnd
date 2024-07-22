package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;

public record GiocoDTO(
        @NotEmpty(message = "Il nome deve essere un dato obbligatorio!")
        String nome,
        @NotEmpty(message = "La descrizione deve essere un dato obbligatorio!")
        String descrizione
) {
}
