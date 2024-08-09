package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "giochi")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"tornei"})
public class Gioco {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String descrizione;

    @OneToMany(mappedBy = "gioco")
    private List<Torneo> tornei;

    public Gioco(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.tornei = new ArrayList<>();
    }
}
