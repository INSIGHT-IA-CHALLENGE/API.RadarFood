package br.com.fiap.RadarFood.models;

import java.util.Calendar;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.RadarFood.controllers.ReservaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import jakarta.persistence.CascadeType;
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
    @ManyToOne(cascade = CascadeType.MERGE)
    private Usuario usuario;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private Alimento alimento;

    @NotNull
    private Calendar dataMaxima;

    @NotNull
    private Calendar dataRetirada;

    @NotNull
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
