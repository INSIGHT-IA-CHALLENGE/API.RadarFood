package br.com.fiap.RadarFood.models;


import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;

import br.com.fiap.RadarFood.controllers.EnderecoController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @Size(min = 1, max = 150, message = "Logradouro deve ter entre 1 e 150 caracteres")
    @NotNull 
    private String logradouro;

    @Size(min = 1, max = 150, message = "Número deve ter entre 1 e 150 caracteres")
    @NotNull 
    private String bairro;

    @Size(min = 1, max = 150, message = "Cidade deve ter entre 1 e 150 caracteres")
    @NotNull 
    private String cidade;

    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
    @NotNull 
    private String uf;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    @NotNull 
    private String cep;

    @NotNull
    @Size(min = 1, max = 10, message = "Número deve ter entre 1 e 10 caracteres")
    private String numero;

    @Column(nullable = true)
    private String complemento;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Boolean ativo;

    public EntityModel<Endereco> toEntityModel(){
        return EntityModel.of(
            this,
            linkTo(methodOn(EnderecoController.class).buscar(id)).withSelfRel(),
            linkTo(methodOn(EnderecoController.class).listar(null, Pageable.unpaged())).withRel("all"),
            linkTo(methodOn(EnderecoController.class).atualizar(id, this)).withRel("update"),
            linkTo(methodOn(EnderecoController.class).apagar(id)).withRel("delete")
        );
    }


    
}
