package br.com.fiap.RadarFood.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.fiap.RadarFood.exception.RestNotFoundException;
import br.com.fiap.RadarFood.models.Alimento;
import br.com.fiap.RadarFood.models.TipoUsuario;
import br.com.fiap.RadarFood.repository.AlimentoRepository;
import br.com.fiap.RadarFood.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alimento")
public class AlimentoController {

    Logger log = LoggerFactory.getLogger(AlimentoController.class);

    @Autowired
    AlimentoRepository repository;

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

        if (pesquisa == null)
            pesquisa = "";

        Page<Alimento> alimentos;

        if (usuario.getTipoUsuario() == TipoUsuario.F)
            alimentos = repository.findByUsuario(usuario, pesquisa, pageable);
        else
            alimentos = repository
                    .findByDescricaoIgnoreCaseContainingOrderByDataValidadeAscValorAscDescricaoAsc(pesquisa, pageable);

        return assembler.toModel(alimentos.map(Alimento::toEntityModel));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Alimento>> cadastrar(@RequestBody Alimento alimento) {

        repository.save(alimento);

        return ResponseEntity
                .created(alimento.toEntityModel().getRequiredLink("self").toUri())
                .body(alimento.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Alimento> buscar(@PathVariable Integer id) {
        log.info("Buscando alimento com id " + id);
        return getAlimento(id).toEntityModel();

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
    public ResponseEntity<Alimento> apagar(@PathVariable Integer id) {
        var alimento = getAlimento(id);
        

        alimento.setAtivo(false);
        repository.save(alimento);

        return ResponseEntity.noContent().build();
    }

    private Alimento getAlimento(Integer id) {
        return repository
                .findById(id)
                .filter(alimento -> alimento.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Alimento não encontrado"));
    }
}
