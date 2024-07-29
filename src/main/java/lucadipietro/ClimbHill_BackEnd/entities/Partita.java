package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "partite")
@Getter
@Setter
@NoArgsConstructor
public class Partita {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "ora_inizio")
    private LocalTime oraInizio;
    private int round;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    @ManyToMany
    @JoinTable(
            name = "partita_utenti",
            joinColumns = @JoinColumn(name = "partita_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> utenti;

    @ManyToMany
    @JoinTable(
            name = "partita_squadre",
            joinColumns = @JoinColumn(name = "partita_id"),
            inverseJoinColumns = @JoinColumn(name = "squadra_id")
    )
    private List<Squadra> squadre;

    @OneToMany(mappedBy = "partita")
    private List<Risultato> risultati;

    public Partita(LocalDate dataInizio, LocalTime oraInizio, Torneo torneo) {
        this.dataInizio = dataInizio;
        this.oraInizio = oraInizio;
        this.round = 1;
        this.torneo = torneo;
        this.utenti = new ArrayList<>();
        this.squadre = new ArrayList<>();
        this.risultati = new ArrayList<>();
    }
}
