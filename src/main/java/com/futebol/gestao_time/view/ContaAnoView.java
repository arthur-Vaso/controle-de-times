package com.futebol.gestao_time.view;

import com.futebol.gestao_time.model.UsuarioComplemento;

import lombok.Data;

@Data
public class ContaAnoView {
    private UsuarioComplemento usuario;
    private String ano;
    private Integer conta;

    public ContaAnoView(UsuarioComplemento usuario, String ano, Integer conta) {
        this.usuario = usuario;
        this.ano = ano;
        this.conta = conta;
    }
}
