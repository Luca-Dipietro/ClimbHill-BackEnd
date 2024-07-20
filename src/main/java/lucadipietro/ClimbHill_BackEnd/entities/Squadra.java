package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Squadra {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "squadra_utenti",
            joinColumns = @JoinColumn(name = "squadra_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private Set<Utente> membri;

    @OneToMany(mappedBy = "squadra")
    private Set<Partecipazione> partecipazioni;

    @ManyToMany(mappedBy = "squadre")
    private Set<Partita> partite;

    @OneToMany(mappedBy = "squadra")
    private Set<Statistica> statistiche;
}
