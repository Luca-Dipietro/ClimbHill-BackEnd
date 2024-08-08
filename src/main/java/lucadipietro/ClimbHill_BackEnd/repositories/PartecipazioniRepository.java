package lucadipietro.ClimbHill_BackEnd.repositories;

import lucadipietro.ClimbHill_BackEnd.entities.Partecipazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartecipazioniRepository extends JpaRepository<Partecipazione, UUID> {
}
