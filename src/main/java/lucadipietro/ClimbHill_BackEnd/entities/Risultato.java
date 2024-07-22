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
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    @ManyToOne
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    public Risultato(int punteggio, Partita partita, Utente utente, Squadra squadra, Statistica statistica) {
        this.punteggio = punteggio;
        this.partita = partita;
        this.utente = utente;
        this.squadra = squadra;
        this.statistica = statistica;
    }
}
