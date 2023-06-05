package br.com.fiap.RadarFood.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import br.com.fiap.RadarFood.repository.EnderecoRepository;
import br.com.fiap.RadarFood.repository.UsuarioRepository;
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
    UsuarioRepository usuarioRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> listar(@RequestParam(required = false) String pesquisa,
            @PageableDefault(size = 5) Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));

        if(pesquisa == null)
            pesquisa = "";

        var enderecos = repository.searchByUsuario(usuario, pesquisa, pageable);

        return assembler.toModel(enderecos.map(Endereco::toEntityModel));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Endereco>> cadastrar(@RequestBody Endereco endereco) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));

        endereco.setUsuario(usuario);
        repository.save(endereco);

        return ResponseEntity
                .created(endereco.toEntityModel().getRequiredLink("self").toUri())
                .body(endereco.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Endereco> buscar(@PathVariable Integer id) {
        log.info("Buscando endereco com id " + id);
        return getEndereco(id).toEntityModel();

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
    public ResponseEntity<Endereco> apagar(@PathVariable Integer id) {
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
