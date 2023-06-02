package br.com.fiap.RadarFood.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.data.domain.Pageable;

import br.com.fiap.RadarFood.repository.EnderecoRepository;
import jakarta.validation.Valid;
import br.com.fiap.RadarFood.exception.RestNotFoundException;
import br.com.fiap.RadarFood.models.Endereco;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {
 
    
    Logger log = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    EnderecoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> listar(@PageableDefault(size = 5) Pageable pageable){

        var enderecos = repository.findAll(pageable);
        return assembler.toModel(enderecos.map(Endereco::toEntityModel));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Endereco>> cadastrar(@RequestBody Endereco endereco){

        log.info("Cadastrando endereço: {}", endereco);

        repository.save(endereco);
        log.info("Endereço cadastrado: {}", endereco);

        return ResponseEntity
                .created(endereco.toEntityModel().getRequiredLink("self").toUri())
                .body(endereco.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Endereco> buscar(@PathVariable Integer id){
        log.info("Buscando endereco com id " + id);
        return getEndereco (id).toEntityModel();

    }

    @PutMapping("{id}")
    public EntityModel<Endereco> atualizar(@PathVariable Integer id, @RequestBody @Valid Endereco endereco) {
        log.info("Atualizando endereco com id " + id);
        getEndereco(id);
        endereco.setId(id);
        repository.save(endereco);

        return endereco.toEntityModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Endereco> apagar(@PathVariable Integer id){
        var endereco = getEndereco(id);
        log.info("Apagando o endereco: " + endereco);

        endereco.setAtivo(false);
        repository.save(endereco);
        
        return ResponseEntity.noContent().build();
    }


    private Endereco getEndereco(Integer id) {
        return repository
                .findById(id)
                .filter(endereco -> endereco.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Endereço não encontrado"));
    }
        
    }

