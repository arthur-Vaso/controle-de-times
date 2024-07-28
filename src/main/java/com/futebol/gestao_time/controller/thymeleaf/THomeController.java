package com.futebol.gestao_time.controller.thymeleaf;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.futebol.gestao_time.model.Usuario;
import com.futebol.gestao_time.repository.IUsuarioRepository;
import com.futebol.gestao_time.service.PagamentoService;
import com.futebol.gestao_time.service.PresencaService;
import com.futebol.gestao_time.service.UsuarioService;
import com.futebol.gestao_time.utils.Mes;
import com.futebol.gestao_time.utils.Resposta;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
		Resposta resposta = usuarioService.buscaTodosAtivos();;

		model.addAttribute("usuarios", resposta.getBody().get("usuarios"));
		return "home";
	}

}