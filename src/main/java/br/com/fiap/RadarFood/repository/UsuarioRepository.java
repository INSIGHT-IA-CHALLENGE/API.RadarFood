package br.com.fiap.RadarFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.RadarFood.models.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer>  {
    
}
