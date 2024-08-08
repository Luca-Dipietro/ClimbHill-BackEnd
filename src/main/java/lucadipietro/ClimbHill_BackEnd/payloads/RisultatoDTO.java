package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record RisultatoDTO(
        @NotNull
        int punteggio,
        @Nullable
        String usernameUtente,
        @Nullable
        String nomeSquadra
) {
}
