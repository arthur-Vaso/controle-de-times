package com.futebol.gestao_time.view;

import com.futebol.gestao_time.model.Usuario;

import lombok.Data;

@Data
public class PresencaContaAnoMesView {
    private Usuario usuario;
    private Integer mes;
    private String ano;
    private Integer conta;

    public PresencaContaAnoMesView(Usuario usuario, Integer mes, String ano, Integer conta) {
        this.usuario = usuario;
        this.mes = mes;
        this.ano = ano;
        this.conta = conta;
    }
}
