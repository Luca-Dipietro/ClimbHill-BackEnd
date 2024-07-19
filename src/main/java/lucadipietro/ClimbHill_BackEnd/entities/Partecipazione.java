package lucadipietro.ClimbHill_BackEnd.entities;

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
public class Partecipazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "data_iscrizione")
    private LocalDate dataIscrizione;
}
