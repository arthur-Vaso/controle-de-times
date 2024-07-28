package com.futebol.gestao_time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.futebol.gestao_time.model.Usuario;
import java.util.List;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT * FROM usuario u " +
            "WHERE u.ativo = :ativo " +
            "ORDER BY u.nome, u.sobrenome", nativeQuery = true)
    List<Usuario> findByAtivo(@Param("ativo") Boolean ativo);

    @Query(value = "SELECT COUNT(id) " +
            "FROM usuario u " +
            "WHERE  u.ativo = true ", nativeQuery = true)
    Integer countAtivos();

    @Query(value = "SELECT COUNT(id) " +
            "FROM usuario u " +
            "WHERE  u.ativo = false ", nativeQuery = true)
    Integer countInativos();

}
