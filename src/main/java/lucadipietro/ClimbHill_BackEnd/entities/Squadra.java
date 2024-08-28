package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"partecipazioni","partite","risultati","membri"})
public class Squadra {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "squadra_utenti",
            joinColumns = @JoinColumn(name = "squadra_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> membri;

    @OneToMany(mappedBy = "squadra")
    private List<Partecipazione> partecipazioni;

    @ManyToMany(mappedBy = "squadre")
    private List<Partita> partite;

    @OneToMany(mappedBy = "squadra",fetch = FetchType.EAGER)
    private List<Statistica> statistiche;

    @OneToMany(mappedBy = "squadra")
    private List<Risultato> risultati;

    public Squadra(String nome) {
        this.nome = nome;
        this.membri = new ArrayList<>();
    }
}
