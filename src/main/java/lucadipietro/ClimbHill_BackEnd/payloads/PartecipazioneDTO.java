package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartecipazioneDTO(
        String usernameUtente,
        String nomeSquadra,
        @NotEmpty(message = "Il nome del torneo deve essere un dato obbligatorio!")
        String nomeTorneo
) {
}
