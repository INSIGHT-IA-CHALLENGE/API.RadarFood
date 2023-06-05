package br.com.fiap.RadarFood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.RadarFood.models.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer>  {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByTelefone(String telefone);
    
}
