package lucadipietro.ClimbHill_BackEnd.repositories;

import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import lucadipietro.ClimbHill_BackEnd.entities.Statistica;
import lucadipietro.ClimbHill_BackEnd.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticheRepository extends JpaRepository<Statistica, UUID> {
    Optional<Statistica> findByUtenteId(UUID utenteId);
    Optional<Statistica> findBySquadraId(UUID squadraId);
}
