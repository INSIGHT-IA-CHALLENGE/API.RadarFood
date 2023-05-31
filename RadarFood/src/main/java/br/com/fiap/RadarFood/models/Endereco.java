package br.com.fiap.RadarFood.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @NotNull
    private Usuario idUsuario;

    @Size(min = 1, max = 150)
    @NotNull 
    private String logradouro;

    @Size(min = 1, max = 150)
    @NotNull 
    private String bairro;

    @Size(min = 1, max = 150)
    @NotNull 
    private String cidade;

    @Pattern(regexp = "\\d{2}")
    @NotNull 
    private String uf;

    @Pattern(regexp = "\\d{9}")
    @NotNull 
    private String cep;

    @NotNull
    @Column(precision = 12, scale = 10)
    private Integer latitude;

    @NotNull
    @Column(precision = 12, scale = 10)
    private Integer longitude;

    @NotNull
    @Pattern(regexp = "\\d{1}")
    private Boolean ativo;


    
}
