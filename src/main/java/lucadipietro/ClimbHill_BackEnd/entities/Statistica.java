package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
}
