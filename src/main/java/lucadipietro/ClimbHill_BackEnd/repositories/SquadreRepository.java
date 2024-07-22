package lucadipietro.ClimbHill_BackEnd.repositories;

import lucadipietro.ClimbHill_BackEnd.entities.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SquadreRepository extends JpaRepository<Squadra, UUID> {
}
