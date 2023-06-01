package br.com.fiap.RadarFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.RadarFood.models.Endereco;

public interface EnderecoRepository extends JpaRepository <Endereco, Integer>  {
    
}
