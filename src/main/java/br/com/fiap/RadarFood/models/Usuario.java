package br.com.fiap.RadarFood.models;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.fiap.RadarFood.controllers.UsuarioController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Size(min = 1, max = 150, message = "Email deve ter entre 1 e 150 caracteres")
    @NotNull
    private String email;

    @Size(min = 5, max = 200, message = "Senha deve ter entre 1 e 200 caracteres")
    @NotNull
    private String senha;

    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    @NotNull
    private String nome;

    @Size(min = 1, max = 15, message = "Telefone deve ter entre 1 e 15 caracteres")
    @NotNull
    @Pattern(regexp = "^\\(\\d{2}\\)\\s(?:9\\d{4}-\\d{4}|\\d{4}-\\d{4})$", message = "Telefone inválido")
    private String telefone;

    @NotNull
    @Column(columnDefinition = "CLOB")
    private String foto;

    @NotNull
    private TipoUsuario tipoUsuario;

    @NotNull
    private Boolean ativo;

    public EntityModel<Usuario> toEntityModel(){
        return EntityModel.of(
            this,
            linkTo(methodOn(UsuarioController.class).buscar(id)).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).atualizar(id, this)).withRel("update")
        );
    }
}
