package br.com.fiap.RadarFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.RadarFood.models.Reserva;

public interface ReservaRepository extends JpaRepository <Reserva, Integer>  {
    
}
