package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartitaDTO(
        @NotEmpty(message = "La data d'inizio deve essere un dato obbligatorio")
        String dataInizio,
        @NotEmpty(message = "L'ora d'inizio deve essere un dato obbligatorio")
        String oraInizio,
        @NotEmpty(message = "Il nome del torneo deve essere un dato obbligatorio")
        String nomeTorneo
) {
}
