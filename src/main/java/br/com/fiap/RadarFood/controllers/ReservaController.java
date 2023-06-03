package br.com.fiap.RadarFood.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.RadarFood.exception.RestNotFoundException;
import br.com.fiap.RadarFood.models.Reserva;
import br.com.fiap.RadarFood.repository.ReservaRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/reserva")

public class ReservaController {
    
    Logger log = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    ReservaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> listar(@PageableDefault(size = 5) Pageable pageable){

        var alimentos = repository.findAll(pageable);
        return assembler.toModel(alimentos.map(Reserva::toEntityModel));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Reserva>> cadastrar(@RequestBody Reserva reserva){

        log.info("Cadastrando reserva: {}", reserva);

        repository.save(reserva);
        log.info("Reserva cadastrada: {}", reserva);

        return ResponseEntity
                .created(reserva.toEntityModel().getRequiredLink("self").toUri())
                .body(reserva.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Reserva> buscar(@PathVariable Integer id){
        log.info("Buscando reserva com id " + id);
        return getReserva (id).toEntityModel();

    }

    
    @PutMapping("{id}")
    public EntityModel<Reserva> atualizar(@PathVariable Integer id, @RequestBody @Valid Reserva reserva) {
        log.info("Atualizando reserva com id " + id);
        getReserva(id);
        reserva.setId(id);
        repository.save(reserva);
        
        return reserva.toEntityModel();
        
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Reserva> apagar(@PathVariable Integer id){
        var reserva = getReserva(id);
        log.info("Apagando a reserva: " + reserva);
        
        reserva.setAtivo(false);
        repository.save(reserva);
        
        return ResponseEntity.noContent().build();
    }
    
    private Reserva getReserva(Integer id) {
        return repository
                .findById(id)
                .filter(reserva -> reserva.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Reserva não encontrado"));
    }
}
