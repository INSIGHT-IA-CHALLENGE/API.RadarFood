package br.com.fiap.RadarFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.RadarFood.models.Alimento;

public interface AlimentoRepository extends JpaRepository <Alimento, Integer> {
    
}
