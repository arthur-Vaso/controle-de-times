package com.futebol.gestao_time.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.futebol.gestao_time.model.Pagamento;

public interface IPagamentoRepository extends JpaRepository<Pagamento, Integer>{

    List<Pagamento> findAllByIdUsuario(Integer id);

}
