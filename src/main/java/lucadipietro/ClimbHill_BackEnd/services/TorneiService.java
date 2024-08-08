package lucadipietro.ClimbHill_BackEnd.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lucadipietro.ClimbHill_BackEnd.entities.Gioco;
import lucadipietro.ClimbHill_BackEnd.entities.Torneo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.TorneoDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.TorneiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TorneiService {

    @Autowired
    private TorneiRepository torneiRepository;

    @Autowired
    private GiochiService giochiService;

    @Autowired
    EntityManager entityManager;

    public Torneo save(TorneoDTO body){
        if(LocalDate.parse(body.dataInizioIscrizione()).isAfter(LocalDate.parse(body.dataFineIscrizione()))){
         throw new BadRequestException("La data di inizio iscrizione non può essere dopo la data di fine iscrizione.");
        }
        Gioco found = giochiService.findByNome(body.nomeGioco());
        Torneo nuovoTorneo = new Torneo(body.nome(),
                body.descrizione(),
                LocalDate.parse(body.dataInizioIscrizione()),
                LocalDate.parse(body.dataFineIscrizione()),
                TipoTorneo.getTipoTorneo(body.tipoTorneo()),
                body.numeroMaxPartecipanti(),
                found);
        return this.torneiRepository.save(nuovoTorneo);
    }

    public Torneo findById(UUID torneoId) {
        return this.torneiRepository.findById(torneoId).orElseThrow(() -> new NotFoundException(torneoId));
    }

    public Torneo findByNome(String nome){
        return this.torneiRepository.findByNome(nome).orElseThrow(() -> new NotFoundException(nome));
    }

    public Page<Torneo> getTornei(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return torneiRepository.findAll(pageable);
    }

    public Torneo findByIdAndUpdate(UUID torneoId, TorneoDTO body) {
        Gioco giocoFound = giochiService.findByNome(body.nomeGioco());
        Torneo torneoFound = this.findById(torneoId);
        if(LocalDate.parse(body.dataInizioIscrizione()).isAfter(LocalDate.parse(body.dataFineIscrizione()))){
            throw new BadRequestException("La data di inizio iscrizione non può essere dopo la data di fine iscrizione.");
        }
        torneoFound.setNome(body.nome());
        torneoFound.setDescrizione(body.descrizione());
        torneoFound.setDataInizioIscrizione(LocalDate.parse(body.dataInizioIscrizione()));
        torneoFound.setDataFineIscrizione(LocalDate.parse(body.dataFineIscrizione()));
        torneoFound.setTipoTorneo(TipoTorneo.getTipoTorneo(body.tipoTorneo()));
        torneoFound.setNumeroMaxPartecipanti(body.numeroMaxPartecipanti());
        torneoFound.setGioco(giocoFound);
        return this.torneiRepository.save(torneoFound);
    }

    public void findByIdAndDelete(UUID torneoId) {
        Torneo found = this.findById(torneoId);
        this.torneiRepository.delete(found);
    }

    public Page<Torneo> searchTornei(String nome, TipoTorneo tipoTorneo, LocalDate dataInizioIscrizione, LocalDate dataFineIscrizione, int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Torneo> cq = cb.createQuery(Torneo.class);
        Root<Torneo> torneo = cq.from(Torneo.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null && !nome.isEmpty()) {
            predicates.add(cb.like(torneo.get("nome"), "%" + nome + "%"));
        }

        if (tipoTorneo != null) {
            predicates.add(cb.equal(torneo.get("tipoTorneo"), tipoTorneo));
        }

        if (dataInizioIscrizione != null) {
            predicates.add(cb.greaterThanOrEqualTo(torneo.get("dataInizioIscrizione"), dataInizioIscrizione));
        }

        if (dataFineIscrizione != null) {
            predicates.add(cb.lessThanOrEqualTo(torneo.get("dataFineIscrizione"), dataFineIscrizione));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Torneo> query = entityManager.createQuery(cq);
        List<Torneo> resultList = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList, pageable, query.getResultList().size());
    }
}
