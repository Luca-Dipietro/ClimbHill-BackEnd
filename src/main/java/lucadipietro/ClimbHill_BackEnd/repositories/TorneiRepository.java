package lucadipietro.ClimbHill_BackEnd.repositories;

import lucadipietro.ClimbHill_BackEnd.entities.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TorneiRepository extends JpaRepository<Torneo, UUID> {
    Optional<Torneo> findByNome(String nome);
}
