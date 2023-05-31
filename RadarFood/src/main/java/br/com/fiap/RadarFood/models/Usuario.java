package br.com.fiap.RadarFood.models;

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
    @Size(min = 1, max = 150)
    @NotNull
    private String email;

    @Size(min = 1, max = 200)
    @NotNull
    private String senha;

    @Size(min = 1, max = 100)
    @NotNull
    private String nome;

    @Size(min = 1, max = 15)
    @NotNull
    private String telefone;

    @NotNull
    private String foto;

    @NotNull
    @Pattern(regexp = "\\d{1}")
    private Boolean ativo;

}
