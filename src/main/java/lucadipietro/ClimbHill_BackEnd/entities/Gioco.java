package lucadipietro.ClimbHill_BackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "giochi")
@Getter
@Setter
@NoArgsConstructor
public class Gioco {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String descrizione;

    @OneToMany(mappedBy = "gioco")
    private Set<Torneo> tornei;
}
