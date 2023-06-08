package br.com.fiap.RadarFood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.RadarFood.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r "
            + "JOIN FETCH r.alimento a "
            + "JOIN FETCH a.endereco e "
            + "WHERE a.ativo = true "
            + "AND e.ativo = true "
            + "AND e.usuario.ativo = true "
            + "AND a.id = :id")
    Optional<Reserva> findByAlimento(@Param("id") Integer id);

}
