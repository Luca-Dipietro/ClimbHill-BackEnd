package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"utenti"})
public class Ruolo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TipoRuolo ruolo;

    @ManyToMany(mappedBy = "ruoli")
    private List<Utente> utenti;

    public Ruolo(TipoRuolo ruolo) {
        this.ruolo = ruolo;
    }
}
