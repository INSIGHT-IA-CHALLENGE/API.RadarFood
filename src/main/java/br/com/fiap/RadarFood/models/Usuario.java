package br.com.fiap.RadarFood.models;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;

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
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Email(message = "Email inválido")
    @Size(min = 1, max = 150, message = "Email deve ter entre 1 e 150 caracteres")
    @Column(unique = true)
    private String email;

    @Size(min = 5, max = 200, message = "Senha deve ter entre 1 e 200 caracteres")
    @NotNull
    private String senha;

    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    @NotNull
    private String nome;

    @NotNull
    @Size(min = 1, max = 15, message = "Telefone deve ter entre 1 e 15 caracteres")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s(?:9\\d{4}-\\d{4}|\\d{4}-\\d{4})$", message = "Telefone inválido")
    @Column(unique = true)
    private String telefone;
    
    @NotNull
    private TipoUsuario tipoUsuario;
    
    @NotNull
    private Boolean ativo;

    @NotNull
    @Column(columnDefinition = "CLOB")
    private String foto;

    public EntityModel<Usuario> toEntityModel(){
        return EntityModel.of(
            this,
            linkTo(methodOn(UsuarioController.class).buscar()).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).atualizar(id, this)).withRel("update"),
            linkTo(methodOn(UsuarioController.class).apagar(id)).withRel("delete")
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.toString()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return ativo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ativo;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
