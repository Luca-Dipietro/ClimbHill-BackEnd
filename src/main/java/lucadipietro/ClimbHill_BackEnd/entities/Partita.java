package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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
}
