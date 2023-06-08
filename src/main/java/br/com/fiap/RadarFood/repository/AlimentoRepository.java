package br.com.fiap.RadarFood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.RadarFood.models.Alimento;
import br.com.fiap.RadarFood.models.Usuario;

public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {

    @Query("SELECT a FROM Alimento a "
            + "WHERE a.endereco.usuario = :usuario "
            + "AND a.endereco.ativo = true "
            + "AND UPPER(a.descricao) LIKE UPPER(concat('%', :pesquisa, '%')) "
            + "AND a.ativo = true "
            + "AND a.dataValidade > CURRENT_DATE "
            + "ORDER BY a.dataValidade, COALESCE(a.valor, 0), a.descricao ASC")
    Page<Alimento> findByUsuario(@Param("usuario") Usuario usuario, @Param("pesquisa") String pesquisa,
            Pageable pageable);

    Page<Alimento> findByDescricaoIgnoreCaseContainingOrderByDataValidadeAscValorAscDescricaoAsc(String pesquisa,
            Pageable pageable);
}
