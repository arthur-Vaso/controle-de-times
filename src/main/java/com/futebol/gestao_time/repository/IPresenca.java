package com.futebol.gestao_time.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.futebol.gestao_time.model.Presenca;

import jakarta.persistence.Tuple;

public interface IPresenca extends JpaRepository<Presenca, Integer> {

        @Query(value = "SELECT * FROM presencas p WHERE p.id_usuario_complemento = :id ORDER BY p.ano DESC, p.mes ASC, p.id DESC", nativeQuery = true)
        List<Presenca> findByIdUsuarioOderByAnoDescMes(Integer id);

        @Query(value = "SELECT * FROM presencas p WHERE p.id_usuario_complemento = :id", nativeQuery = true)
        List<Presenca> buscaTodosUniaoUsuario(@Param("id") Integer id);

        @Query(value = "SELECT * FROM presencas p ORDER BY p.ano DESC, p.mes DESC, p.id_usuario_complemento ASC", nativeQuery = true)
        List<Presenca> buscaOrdenadoPorAnoEMes();

        @Query("SELECT p.usuario as usuario, p.mes as mes, p.ano as ano, count(p) as conta " +
                        "FROM Presenca p " +
                        "WHERE p.usuario.id = :idUsuario " +
                        "GROUP BY p.usuario, p.mes, p.ano " +
                        "ORDER BY p.ano desc, p.mes asc")
        List<Tuple> countByIdUsuarioAndAnoAndMes(@Param("idUsuario") Integer idUsuario);

        @Query("SELECT p.usuario as usuario, p.ano as ano, count(p) as conta " +
                        "FROM Presenca p " +
                        "WHERE p.usuario.id = :idUsuario " +
                        "GROUP BY p.usuario, p.ano " +
                        "ORDER BY p.ano desc")
        List<Tuple> countByIdUsuarioAndAno(@Param("idUsuario") Integer idUsuario);

        @Query(value = "SELECT * FROM presencas p WHERE p.id_usuario_complemento = :idUsuario;", nativeQuery = true)
        Presenca findByIdUsuario(Integer idUsuario);

        @Query(value = "SELECT MIN(p.ano) FROM presencas p", nativeQuery = true)
        String buscarAnoMaisAntigo();

        @Query(value = "SELECT MAX(p.ano) FROM presencas p", nativeQuery = true)
        String buscarAnoMaisRecente();

        @Query(value = "select p.id_usuario_complemento from presencas p " +
                        "where p.mes = :mes and p.ano = :ano " +
                        "group by p.id_usuario_complemento ", nativeQuery = true)
        List<Integer> buscarIdUsuarioComplemento(@Param("mes") Integer mes, @Param("ano") String ano);

}
