package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tornei")
@Getter
@Setter
@NoArgsConstructor
public class Torneo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String descrizione;
    @Column(name = "data_inizio_iscrizione")
    private LocalDate dataInizioIscrizione;
    @Column(name = "data_fine_iscrizione")
    private LocalDate dataFineIscrizione;
    @Enumerated(EnumType.STRING)
    private TipoTorneo tipoTorneo;

    @ManyToOne
    @JoinColumn(name = "gioco_id")
    private Gioco gioco;

    @OneToMany(mappedBy = "torneo")
    private Set<Partecipazione> partecipazioni;

    @OneToMany(mappedBy = "torneo")
    private Set<Partita> partite;

    public Torneo(String nome, String descrizione, LocalDate dataInizioIscrizione, LocalDate dataFineIscrizione, TipoTorneo tipoTorneo, Gioco gioco) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataInizioIscrizione = dataInizioIscrizione;
        this.dataFineIscrizione = dataFineIscrizione;
        this.tipoTorneo = tipoTorneo;
        this.gioco = gioco;
        this.partecipazioni = new HashSet<>();
        this.partite = new HashSet<>();
    }
}
