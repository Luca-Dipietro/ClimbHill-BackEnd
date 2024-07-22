package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SquadraDTO(
        @NotEmpty(message = "Il nome deve essere un dato obbligatorio!")
        @Size(min = 4,max = 20,message = "Il nome deve essere compreso tra i 4 e 20 caratteri!")
        String nome
) {
}
