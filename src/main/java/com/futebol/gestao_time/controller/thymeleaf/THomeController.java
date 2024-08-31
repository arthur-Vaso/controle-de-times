package com.futebol.gestao_time.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futebol.gestao_time.service.UsuarioService;
import com.futebol.gestao_time.utils.Resposta;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/home")
public class THomeController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("")
	public String home(Model model) {

		model.addAttribute("ativos", usuarioService.contaAtivos().getBody().get("ativos"));
		model.addAttribute("inativos", usuarioService.contaInativos().getBody().get("inativos"));

		return "home";
	}

	@PostMapping("")
	public String homeLogin(Model model) {
		Resposta resposta = usuarioService.buscaTodosAtivos();

		model.addAttribute("usuarios", resposta.getBody().get("usuarios"));
		return "home";
	}

}