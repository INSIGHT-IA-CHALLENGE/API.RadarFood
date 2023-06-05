package br.com.fiap.RadarFood.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.RadarFood.exception.RestNotFoundException;
import br.com.fiap.RadarFood.models.Credencial;
import br.com.fiap.RadarFood.models.Token;
import br.com.fiap.RadarFood.models.Usuario;
import br.com.fiap.RadarFood.repository.UsuarioRepository;
import br.com.fiap.RadarFood.security.TokenService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<EntityModel<Usuario>> cadastrar(@RequestBody Usuario usuario) {

        if (!repository.findByEmail(usuario.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (!repository.findByTelefone(usuario.getTelefone()).isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        try {
            usuario.setSenha(encoder.encode(usuario.getSenha()));
            repository.save(usuario);

        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity
                .created(usuario.toEntityModel().getRequiredLink("self").toUri())
                .body(usuario.toEntityModel());
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid Credencial credencial) {

        if (repository.findByEmail(credencial.email()).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        manager.authenticate(credencial.toAuthentication());

        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public EntityModel<Usuario> buscar() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            var usuario = repository.findByEmail(email)
                    .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));

            return usuario.toEntityModel();
        } catch (Exception e) {
            return null;
        }

    }

    @PutMapping("{id}")
    public EntityModel<Usuario> atualizar(@PathVariable Integer id, @RequestBody @Valid Usuario usuario) {
        log.info("Atualizando usuario com id " + id);
        getUsuario(id);
        usuario.setId(id);
        repository.save(usuario);

        return usuario.toEntityModel();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Usuario> apagar(@PathVariable Integer id) {
        var usuario = getUsuario(id);
        log.info("Apagando o usuario: " + usuario);

        usuario.setAtivo(false);
        repository.save(usuario);

        return ResponseEntity.noContent().build();

    }

    private Usuario getUsuario(Integer id) {
        return repository
                .findById(id)
                .filter(usuario -> usuario.getAtivo())
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrada"));
    }
}
