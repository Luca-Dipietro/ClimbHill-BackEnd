package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;

public record RuoloDTO(
        @NotEmpty(message = "Il ruolo deve essere un dato obbligatorio!")
        String ruolo
) {
}
