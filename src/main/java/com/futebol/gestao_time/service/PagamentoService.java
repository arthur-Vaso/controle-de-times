package com.futebol.gestao_time.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.model.Pagamento;
import com.futebol.gestao_time.repository.IPagamento;
import com.futebol.gestao_time.utils.Resposta;

@Service
public class PagamentoService {

    @Autowired
    private IPagamento repository;

    public Resposta buscarPagamentoPorIdUsuario(Integer id) {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        List<Pagamento> pagamentos = repository.findAllByIdUsuario(id);
        Set<String> anos = new HashSet<>();

        for(Pagamento pagamento : pagamentos) {
            anos.add(pagamento.getAno());
        }

        resposta.setStatus(HttpStatus.OK);
        body.put("pagamentos", pagamentos);
        body.put("anosPagamento", anos);
        resposta.setBody(body);

        return resposta;
    }

}
