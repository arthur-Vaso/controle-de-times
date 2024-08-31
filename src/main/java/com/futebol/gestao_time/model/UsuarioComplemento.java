package com.futebol.gestao_time.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios_complementos")
public class UsuarioComplemento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
	private Usuario idUsuario;

	@Column(name="nome")
	private String nome;

	@Column(name="sobrenome")
	private String sobrenome;

	@Column(name="telefone")
	private String telefone;

	@Column(name="e_whatsapp")
	private Boolean eWhatshapp;

}
