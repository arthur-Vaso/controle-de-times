package com.futebol.gestao_time.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="nome")
	private String nome;

	@Column(name="sobrenome")
	private String sobrenome;

	@Column(name="telefone")
	private String telefone;

	@Column(name="e_whatsapp")
	private Boolean eWhatshapp;

	@Column(name="ativo")
	private Boolean ativo;

	@Column(name="senha")
	private String senha;

}
