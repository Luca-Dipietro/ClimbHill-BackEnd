package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"utente", "squadra","partecipazioni"})
public class Statistica {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private int vittorie;
    @Column(name = "partite_giocate")
    private int numeroPartiteGiocate;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "statistica")
    private List<Partecipazione> partecipazioni;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    public Statistica(Utente utente) {
        this.partecipazioni = new ArrayList<>();
        this.utente = utente;
        this.vittorie = 0;
        this.numeroPartiteGiocate = 0;
    }

    public Statistica(Squadra squadra) {
        this.partecipazioni = new ArrayList<>();
        this.squadra = squadra;
        this.vittorie = 0;
        this.numeroPartiteGiocate = 0;
    }

    public void incrementaVittorie() {
        this.vittorie++;
    }

    public void incrementaPartiteGiocate() {
        this.numeroPartiteGiocate++;
    }
}
