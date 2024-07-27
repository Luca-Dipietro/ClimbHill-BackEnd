package lucadipietro.ClimbHill_BackEnd.enums;

import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;

public enum TipoTorneo {
    TORNEO_SINGOLO,TORNEO_A_SQUADRE;

    public static TipoTorneo getTipoTorneo(String tipoTorneo) {
        if (tipoTorneo.equalsIgnoreCase("torneo_singolo")) {
            return TipoTorneo.TORNEO_SINGOLO;
        } else if (tipoTorneo.equalsIgnoreCase("torneo_a_squadre")) {
            return TipoTorneo.TORNEO_A_SQUADRE;
        } else {
            throw new BadRequestException("I Tipi di torneo possibili sono: TORNEO_SINGOLO O TORNEO_A_SQUADRE");
        }
    }
}
