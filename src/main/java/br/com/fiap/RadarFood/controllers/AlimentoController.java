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
import br.com.fiap.RadarFood.models.Alimento;
import br.com.fiap.RadarFood.repository.AlimentoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alimento")
public class AlimentoController {

    Logger log = LoggerFactory.getLogger(AlimentoController.class);

    @Autowired
    AlimentoRepository repository;

    
    @Autowired
    PagedResourcesAssembler<Object> assembler;


    @GetMapping
    public PagedModel<EntityModel<Object>> listar(@PageableDefault(size = 5) Pageable pageable){

        var alimentos = repository.findAll(pageable);
        return assembler.toModel(alimentos.map(Alimento::toEntityModel));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Alimento>> cadastrar(@RequestBody Alimento alimento){

        log.info("Cadastrando alimento: {}", alimento);

        repository.save(alimento);
        log.info("Alimento cadastrado: {}", alimento);

        return ResponseEntity
                .created(alimento.toEntityModel().getRequiredLink("self").toUri())
                .body(alimento.toEntityModel());
    }


    @GetMapping("{id}")
    public EntityModel<Alimento> buscar(@PathVariable Integer id){
        log.info("Buscando endereco com id " + id);
        return getAlimento (id).toEntityModel();

    }

    
    @PutMapping("{id}")
    public EntityModel<Alimento> atualizar(@PathVariable Integer id, @RequestBody @Valid Alimento alimento) {
        log.info("Atualizando alimento com id " + id);
        getAlimento(id);
        alimento.setId(id);
        repository.save(alimento);
        
        return alimento.toEntityModel();
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Alimento> apagar(@PathVariable Integer id){
        var alimento = getAlimento(id);
        log.info("Apagando o alimento: " + alimento);

        alimento.setAtivo(false);
        repository.save(alimento);
        
        return ResponseEntity.noContent().build();
    }
    
    private Alimento getAlimento(Integer id) {
        return repository
                .findById(id)
                .filter(endereco -> endereco.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Endereço não encontrado"));
    }
}
