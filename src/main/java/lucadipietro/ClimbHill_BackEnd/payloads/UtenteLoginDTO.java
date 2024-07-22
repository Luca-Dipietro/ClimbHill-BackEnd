package lucadipietro.ClimbHill_BackEnd.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteLoginDTO(
        @NotEmpty(message = "L'email è un dato obbligatorio!")
        @Email(message = "L'email inserita non è valida!")
        String email,
        @NotEmpty(message = "La password è un dato obbligatorio!")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri!")
        String password
) {
}
