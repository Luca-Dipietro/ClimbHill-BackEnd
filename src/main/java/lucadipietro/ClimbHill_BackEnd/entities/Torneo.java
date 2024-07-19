package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
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
}
