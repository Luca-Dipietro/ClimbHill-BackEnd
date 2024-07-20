package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "statistiche")
@Getter
@Setter
@NoArgsConstructor
public class Statistica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "gioco_id")
    private Gioco gioco;

    @OneToMany(mappedBy = "statistica")
    private Set<Risultato> risultati;

    @OneToMany(mappedBy = "statistica")
    private Set<Partecipazione> partecipazioni;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;
}
