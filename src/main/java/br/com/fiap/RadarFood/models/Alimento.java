package br.com.fiap.RadarFood.models;

import java.util.Calendar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull 
    @ManyToOne(cascade = CascadeType.MERGE)
    private Endereco endereco;

    @NotNull 
    @Size(min = 1, max = 1000, message = "Descrição deve ter entre 1 e 1000 caracteres")
    private String descricao;

    @NotNull 
    @Column(precision = 5)
    private Integer quantidade;

    @NotNull 
    private Calendar dataValidade;

    @NotNull 
    private Double valor;

    @NotNull
    @Column(columnDefinition = "CLOB")
    private String foto;

    @NotNull 
    private Boolean ativo;

}
