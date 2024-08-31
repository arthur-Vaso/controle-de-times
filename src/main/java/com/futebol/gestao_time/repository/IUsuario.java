package com.futebol.gestao_time.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.futebol.gestao_time.model.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Integer> {
    @Query(value = "SELECT * FROM usuarios u " +
                   "WHERE u.ativo = :ativo " +
                   "ORDER BY u.usuario", nativeQuery = true)
    List<Usuario> findByAtivo(boolean ativo);

    @Query(value = "SELECT COUNT(id) " +
                   "FROM usuarios u " +
                   "WHERE  u.ativo = true ", nativeQuery = true)
    Object countAtivos();

    @Query(value = "SELECT COUNT(id) " +
                   "FROM usuarios u " +
                   "WHERE  u.ativo = false ", nativeQuery = true)
    Object countInativos();

}
