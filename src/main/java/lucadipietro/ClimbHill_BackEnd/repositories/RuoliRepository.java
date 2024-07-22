package lucadipietro.ClimbHill_BackEnd.repositories;

import lucadipietro.ClimbHill_BackEnd.entities.Ruolo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoRuolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoliRepository extends JpaRepository<Ruolo, UUID> {
    Optional<Ruolo> findByRole(TipoRuolo ruolo);
}
