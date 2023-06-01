package br.com.fiap.RadarFood.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(cascade = CascadeType.MERGE)
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
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Boolean ativo;


    
}
