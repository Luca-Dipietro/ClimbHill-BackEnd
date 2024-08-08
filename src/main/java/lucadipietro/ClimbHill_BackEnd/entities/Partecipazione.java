package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "partecipazioni")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"torneo"})
public class Partecipazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "data_iscrizione")
    private LocalDate dataIscrizione;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    @ManyToOne
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    public Partecipazione(Utente utente, Torneo torneo) {
        this.dataIscrizione = LocalDate.now();
        this.utente = utente;
        this.torneo = torneo;
    }

    public Partecipazione(Squadra squadra, Torneo torneo) {
        this.dataIscrizione = LocalDate.now();
        this.squadra = squadra;
        this.torneo = torneo;
    }
}
