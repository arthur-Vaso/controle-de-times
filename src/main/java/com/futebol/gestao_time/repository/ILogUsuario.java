package com.futebol.gestao_time.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.futebol.gestao_time.model.LogUsuario;

public interface ILogUsuario extends JpaRepository<LogUsuario, Integer>{

}
