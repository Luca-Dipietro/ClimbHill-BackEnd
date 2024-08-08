package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.annotation.Nullable;

public record StatisticaDTO(
        @Nullable
        String usernameUtente,
        @Nullable
        String nomeSquadra
) {
}
