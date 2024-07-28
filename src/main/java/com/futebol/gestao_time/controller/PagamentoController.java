package com.futebol.gestao_time.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebol.gestao_time.service.PagamentoService;
import com.futebol.gestao_time.utils.Resposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/listar/")
    public Resposta listarPagamento(@RequestParam String id) {
        Resposta resposta = pagamentoService.buscarPagamentoPorIdUsuario(Integer.parseInt(id));
        return resposta;
    }
    
}
