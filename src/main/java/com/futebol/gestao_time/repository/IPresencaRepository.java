package com.futebol.gestao_time.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.futebol.gestao_time.model.Presenca;
import com.futebol.gestao_time.model.Usuario;
import com.futebol.gestao_time.view.PresencaContaAnoMesView;

import jakarta.persistence.Tuple;

public interface IPresencaRepository extends JpaRepository<Presenca, Integer> {

    @Query(value = "SELECT * FROM presenca p WHERE p.id_usuario = :id ORDER BY p.ano DESC, p.mes ASC", nativeQuery = true)
    List<Presenca> findByIdUsuarioOderByAnoDescMes(Integer id);

    @Query(value = "SELECT * FROM presenca p WHERE p.id_usuario = :id", nativeQuery = true)
    List<Presenca> buscaTodosUniaoUsuario(@Param("id") Integer id);

    // @Query(value = "SELECT p id, Date presenca, Usuario usuario, Integer mes,
    // String ano, String observacao FROM presenca p WHERE p.id_usuario = :id",
    // nativeQuery = true)
    // @Query("SELECT new com.exemplo.PresencaView(p.id, p.presenca, p.usuario,
    // p.mes, p.ano, p.observacao) FROM presenca p WHERE p.id_usuario = :idUsuario")
    // List<PresencaView> findAllPresencaByUsuarioId(@Param("idUsuario") Integer
    // idUsuario);

    @Query(value = "SELECT * FROM presenca p ORDER BY p.ano DESC, p.mes DESC, p.id_usuario ASC", nativeQuery = true)
    List<Presenca> buscaOrdenadoPorAnoEMes();

    // @Query("select new com.futebol.view.PresencaView(p.id_usuario, p.ano, p.mes,
    // count(p)) AS conta from presenca p " +
    // "where p.id_usuario = :idUsuario " +
    // "group by p.id_usuario, p.ano, p.mes " +
    // "order by p.ano desc, p.mes asc")
    // List<PresencaView> countByIdUsuarioAndAnoAndMes(@Param("idUsuario") Integer
    // idUsuario);
    @Query("select p.usuario as usuario, p.mes as mes, p.ano as ano, count(p) as conta " +
            "from Presenca p " +
            "where p.usuario.id = :idUsuario " +
            "group by p.usuario, p.mes, p.ano " +
            "order by p.ano desc, p.mes asc")
    List<Tuple> countByIdUsuarioAndAnoAndMes(@Param("idUsuario") Integer idUsuario);

    @Query("select p.usuario as usuario, p.ano as ano, count(p) as conta " +
            "from Presenca p " +
            "where p.usuario.id = :idUsuario " +
            "group by p.usuario, p.ano " +
            "order by p.ano desc")
    List<Tuple> countByIdUsuarioAndAno(@Param("idUsuario") Integer idUsuario);

    // select p.id_usuario, p.ano, count(*) from presenca p where p.id_usuario = 1
    // group by p.id_usuario, p.ano order by p.ano desc;

}
