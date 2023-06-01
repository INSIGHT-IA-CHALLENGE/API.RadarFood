package br.com.fiap.RadarFood.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.RadarFood.exception.RestNotFoundException;
import br.com.fiap.RadarFood.models.Usuario;
import br.com.fiap.RadarFood.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioRepository repository;
    
    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Usuario>> cadastrar(@RequestBody Usuario usuario) {
        log.info("Cadastrando usuário: {}", usuario);

        repository.save(usuario);
        log.info("Usuário cadastrado: {}", usuario);
        
        return ResponseEntity
                .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                .body(usuario.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Usuario> buscar(@PathVariable Integer id) {
        log.info("Buscando usuario com id " + id);
        return getUsuario(id).toEntityModel();

    }

    @PutMapping("{id}")
    public EntityModel<Usuario> atualizar(@PathVariable Integer id, @RequestBody @Valid Usuario usuario) {
        log.info("Atualizando usuario com id " + id);
        getUsuario(id);
        usuario.setId(id);
        repository.save(usuario);

        return usuario.toEntityModel();

    }

    private Usuario getUsuario(Integer id) {
        return repository
                .findById(id)
                .filter(usuario -> usuario.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));
    }
}
