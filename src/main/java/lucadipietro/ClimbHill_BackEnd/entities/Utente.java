package lucadipietro.ClimbHill_BackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;
    private String avatar;

    @ManyToMany(mappedBy = "membri")
    private Set<Squadra> squadre;

    @OneToMany(mappedBy = "utente")
    private Set<Partecipazione> partecipazioni;

    @ManyToMany(mappedBy = "utenti")
    private Set<Partita> partite;

    @OneToMany(mappedBy = "utente")
    private Set<Statistica> statistiche;

    @ManyToMany
    @JoinTable(
            name = "utente_ruoli",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id")
    )
    private Set<Ruolo> ruoli;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
