package br.com.fiap.RadarFood.models;

import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Usuario idUsuario;

    @NotNull
    private Calendar dataMaxima;

    @NotNull
    private Calendar dataRetirada;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Alimento idAlimento;
    

}
