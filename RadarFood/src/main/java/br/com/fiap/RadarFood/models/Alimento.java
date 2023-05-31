package br.com.fiap.RadarFood.models;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alimento {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull 
    private Endereco idEndereco;

    @NotNull 
    @Size(min = 1, max = 1000)
    private String descricao;

    @NotNull 
    @Size(min = 1, max = 5)
    private Integer quantidade;

    @NotNull 
    private Calendar dataValidade;

    @NotNull 
    @Column(precision = 6, scale = 2)
    private Integer valor;

    @NotNull 
    private Boolean ativo;


    
}
