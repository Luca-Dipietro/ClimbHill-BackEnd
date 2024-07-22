package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TorneoDTO(
        @NotEmpty(message = "Il nome deve essere un dato obbligatorio!")
        @Size(min = 5,max = 20,message = "Il nome deve essere compreso tra i 5 e 20 caratteri!")
        String nome,
        @NotEmpty(message = "La descrizione deve essere un dato obbligatorio")
        String descrizione,
        @NotEmpty(message = "La data di inizio iscrizione deve essere un dato obbligatorio")
        String dataInizioIscrizione,
        @NotEmpty(message = "La data di fine iscrizione deve essere un dato obbligatorio")
        String dataFineIscrizione,
        @NotEmpty(message = "Il tipo di torneo deve essere un dato obbligatorio")
        String tipoTorneo,
        @NotEmpty(message = "Il nome del gioco deve essere un dato obbligatorio")
        String nomeGioco
) {
}
