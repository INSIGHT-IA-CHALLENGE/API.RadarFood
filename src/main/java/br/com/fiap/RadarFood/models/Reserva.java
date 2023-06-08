package br.com.fiap.RadarFood.models;

import java.util.Calendar;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fiap.RadarFood.controllers.ReservaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Alimento alimento;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Calendar dataMaxima;

    @Nullable
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Calendar dataRetirada;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean ativo;

    public EntityModel<Reserva> toEntityModel(){
        return EntityModel.of(this, 
            linkTo(methodOn(ReservaController.class).buscar(id)).withSelfRel(),
            linkTo(methodOn(ReservaController.class).listar(Pageable.unpaged())).withRel("all"),
            linkTo(methodOn(ReservaController.class).atualizar(id, this)).withRel("update"),
            linkTo(methodOn(ReservaController.class).apagar(id)).withRel("delete")
        );
    }

}
