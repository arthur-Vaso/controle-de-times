package com.futebol.gestao_time.view;

import com.futebol.gestao_time.model.UsuarioComplemento;

import lombok.Data;

@Data
public class ContaAnoMesView {
    private UsuarioComplemento usuario;
    private Integer mes;
    private String ano;
    private Integer conta;

    public ContaAnoMesView(UsuarioComplemento usuario, Integer mes, String ano, Integer conta) {
        this.usuario = usuario;
        this.mes = mes;
        this.ano = ano;
        this.conta = conta;
    }
}
