package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "risultati")
@Getter
@Setter
@NoArgsConstructor
public class Risultato {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int punteggio;

    @ManyToOne
    @JoinColumn(name = "partita_id")
    private Partita partita;

    @ManyToOne
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;
}
