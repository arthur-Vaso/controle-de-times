package com.futebol.gestao_time.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "presencas")
public class Pagamento {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;

    @Column(name="id_usuario")
    private Integer idUsuario;
    
    @Column(name="data_pagamento")
    private Date dataPagamento;
    
    @Column(name="valor")
    private BigDecimal valor;
    
    @Column(name="forma_pagamento")
    private String formaPagamento;
    
    @Column(name="ano")
    private String ano;
    
    @Column(name="observacao")
    private String observacao;

    @Column(name = "tem_anexo")
    private boolean temAnexo;
}
