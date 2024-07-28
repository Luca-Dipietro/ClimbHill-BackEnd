package lucadipietro.ClimbHill_BackEnd.services;

import lucadipietro.ClimbHill_BackEnd.entities.Gioco;
import lucadipietro.ClimbHill_BackEnd.exceptions.BadRequestException;
import lucadipietro.ClimbHill_BackEnd.exceptions.NotFoundException;
import lucadipietro.ClimbHill_BackEnd.payloads.GiocoDTO;
import lucadipietro.ClimbHill_BackEnd.repositories.GiochiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GiochiService {

    @Autowired
    private GiochiRepository giochiRepository;

    public Gioco save(GiocoDTO body){
        giochiRepository.findByNome(body.nome()).ifPresent(
                gioco -> {
                    throw new BadRequestException("Il gioco " + body.nome() + " è gia presente!");
                });
        Gioco nuovoGioco = new Gioco(body.nome(), body.descrizione());
        return this.giochiRepository.save(nuovoGioco);
    }

    public Gioco findById(UUID giocoId) {
        return this.giochiRepository.findById(giocoId).orElseThrow(() -> new NotFoundException(giocoId));
    }

    public Gioco findByNome(String nome){
        return this.giochiRepository.findByNome(nome).orElseThrow(() -> new NotFoundException(nome));
    }

    public Page<Gioco> getGiochi(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return giochiRepository.findAll(pageable);
    }

    public Gioco findByIdAndUpdate(UUID giocoId, GiocoDTO body) {
        Gioco found = this.findById(giocoId);
        found.setNome(body.nome());
        found.setDescrizione(body.descrizione());
        return this.giochiRepository.save(found);
    }

    public void findByIdAndDelete(UUID giocoId) {
        Gioco found = this.findById(giocoId);
        this.giochiRepository.delete(found);
    }
}
