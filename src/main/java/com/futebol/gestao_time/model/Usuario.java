package com.futebol.gestao_time.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="usuario", length = 25, nullable = false, unique = true)
	private String usuario;

	@Column(name="e_mail", length = 100)
	private String email;

	@Column(name="senha", length = 255, nullable = false)
	private String senha;

	@Column(name="ativo")
	private Boolean ativo;

}
