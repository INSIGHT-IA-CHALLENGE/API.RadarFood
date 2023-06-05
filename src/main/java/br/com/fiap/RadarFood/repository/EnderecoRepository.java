package br.com.fiap.RadarFood.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.RadarFood.models.Endereco;
import br.com.fiap.RadarFood.models.Usuario;

public interface EnderecoRepository extends JpaRepository <Endereco, Integer>  {
    
    @Query("SELECT e FROM Endereco e WHERE e.usuario = :usuario AND " +
       "(UPPER(e.logradouro) LIKE UPPER(concat('%', :pesquisa, '%')) OR " +
       "UPPER(e.bairro) LIKE UPPER(concat('%', :pesquisa, '%')) OR " +
       "UPPER(e.cidade) LIKE UPPER(concat('%', :pesquisa, '%')) OR " +
       "UPPER(e.uf) LIKE UPPER(concat('%', :pesquisa, '%')) OR " +
       "UPPER(e.cep) LIKE UPPER(concat('%', :pesquisa, '%')))")
Page<Endereco> searchByUsuario(@Param("usuario") Usuario usuario, @Param("pesquisa") String pesquisa, Pageable pageable);

}
