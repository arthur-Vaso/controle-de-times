package com.futebol.gestao_time.view;

import com.futebol.gestao_time.model.Usuario;

import lombok.Data;

@Data
public class PresencaContaAnoView {
    private Usuario usuario;
    private String ano;
    private Integer conta;

    public PresencaContaAnoView(Usuario usuario, String ano, Integer conta) {
        this.usuario = usuario;
        this.ano = ano;
        this.conta = conta;
    }
}
