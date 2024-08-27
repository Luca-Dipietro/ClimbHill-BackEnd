package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "tornei")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"partecipazioni","partite"})
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
    @Column(name = "numero_massimo_partecipanti")
    private int numeroMaxPartecipanti;

    @ManyToOne
    @JoinColumn(name = "gioco_id")
    private Gioco gioco;

    @OneToMany(mappedBy = "torneo")
    private List<Partecipazione> partecipazioni;

    @OneToMany(mappedBy = "torneo")
    private List<Partita> partite;

    public Torneo(String nome, String descrizione, LocalDate dataInizioIscrizione, LocalDate dataFineIscrizione, TipoTorneo tipoTorneo,int numeroMaxPartecipanti, Gioco gioco) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataInizioIscrizione = dataInizioIscrizione;
        this.dataFineIscrizione = dataFineIscrizione;
        this.tipoTorneo = tipoTorneo;
        this.numeroMaxPartecipanti = numeroMaxPartecipanti;
        this.gioco = gioco;
        this.partecipazioni = new ArrayList<>();
        this.partite = new ArrayList<>();
    }
}
