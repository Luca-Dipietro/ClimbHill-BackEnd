package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Gioco;
import lucadipietro.ClimbHill_BackEnd.entities.Torneo;
import lucadipietro.ClimbHill_BackEnd.enums.TipoTorneo;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.TorneoDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.TorneiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TorneiService {

    @Autowired
    private TorneiRepository torneiRepository;

    @Autowired
    private GiochiService giochiService;

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
        torneoFound.setGioco(giocoFound);
        return this.torneiRepository.save(torneoFound);
    }

    public void findByIdAndDelete(UUID torneoId) {
        Torneo found = this.findById(torneoId);
        this.torneiRepository.delete(found);
    }
}
