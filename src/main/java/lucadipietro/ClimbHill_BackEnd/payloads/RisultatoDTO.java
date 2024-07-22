package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotNull;

public record RisultatoDTO(
        @NotNull
        int punteggio,
        String usernameUtente,
        String nomeSquadra
) {
}
