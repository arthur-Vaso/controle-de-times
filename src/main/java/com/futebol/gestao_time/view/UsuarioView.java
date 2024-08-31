package com.futebol.gestao_time.view;

import lombok.Data;

@Data
public class UsuarioView {
    private Integer id;
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String telefone;
    private Boolean eWhatshapp;
    private Boolean ativo;

    public UsuarioView() {
    };

    public UsuarioView(Integer id, Integer idUsuario, String nome, String sobrenome, String telefone, Boolean eWhatsapp, Boolean ativo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.eWhatshapp = eWhatsapp;
        this.ativo = ativo;
    }

}
