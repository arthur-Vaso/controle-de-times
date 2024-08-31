package com.futebol.gestao_time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.futebol.gestao_time.model.UsuarioComplemento;

import java.util.List;
import java.util.Optional;

public interface IUsuarioComplemento extends JpaRepository<UsuarioComplemento, Integer> {

        @Query(value = "SELECT * FROM usuarios_complementos uc WHERE uc.id_usuario = :id", nativeQuery = true)
        Optional<UsuarioComplemento> findByIdUsuario(@Param("id") Integer id);
        
        @Query(value = "SELECT uc.id, uc.id_usuario, uc.nome, uc.sobrenome, uc.endereco, uc.telefone, uc.e_whatsapp " +
                        "FROM usuarios_complementos uc " +
                        "JOIN usuarios u on uc.id_usuario = u.id " +
                        "WHERE u.ativo = :ativo " +
                        "ORDER BY uc.nome, uc.sobrenome", nativeQuery = true)
        List<UsuarioComplemento> findByAtivo(@Param("ativo") Boolean ativo);

        @Query(value = "SELECT COUNT(id) " +
                        "FROM usuarios_complementos uc " +
                        "WHERE  uc.id_usuario.ativo = true ", nativeQuery = true)
        Integer countAtivos();

        @Query(value = "SELECT COUNT(id) " +
                        "FROM usuario_complemento uc " +
                        "WHERE  uc.id_usuario.ativo = false ", nativeQuery = true)
        Integer countInativos();

}
