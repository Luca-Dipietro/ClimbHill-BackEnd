package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

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

    @OneToMany(mappedBy = "statistica")
    private List<Risultato> risultati;

    @OneToMany(mappedBy = "statistica")
    private List<Partecipazione> partecipazioni;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    public Statistica(Utente utente) {
        this.risultati = new ArrayList<>();
        this.partecipazioni = new ArrayList<>();
        this.utente = utente;
    }

    public Statistica(Squadra squadra) {
        this.risultati = new ArrayList<>();
        this.partecipazioni = new ArrayList<>();
        this.squadra = squadra;
    }
}
