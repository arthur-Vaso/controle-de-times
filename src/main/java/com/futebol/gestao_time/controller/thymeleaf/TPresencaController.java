package com.futebol.gestao_time.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.futebol.gestao_time.service.PresencaService;
import com.futebol.gestao_time.utils.Mes;
import com.futebol.gestao_time.utils.Resposta;

@Controller
@RequestMapping("/presenca")
public class TPresencaController {

	@Autowired
	private PresencaService presencaService;

	@GetMapping("/listar")
	public String listarPresenca(Model model) {
		Resposta resposta = presencaService.buscarPorAtivos();

		model.addAttribute("presencas", resposta.getBody().get("body"));
		model.addAttribute("nomes", resposta.getBody().get("nomes"));
		model.addAttribute("enummeses", Mes.values());
		model.addAttribute("anos", resposta.getBody()
				.get("anos"));

		return "presenca/presencaListar";
	}

	@GetMapping("/visualizar")
	public String visualizarPresenca(Model model, @RequestParam(required = false) Integer id) {
		Resposta resposta = presencaService.buscaPresencaoUsuario(id);

		model.addAttribute("presencas", resposta.getBody().get("body"));
		model.addAttribute("usuario", resposta.getBody().get("usuario"));
		model.addAttribute("enummeses", Mes.values());
		model.addAttribute("anos", resposta.getBody().get("anos"));
		model.addAttribute("listaAnosMeses", resposta.getBody().get("listaAnosMeses"));
		model.addAttribute("listaAnos", resposta.getBody().get("listaAnos"));
		model.addAttribute("listaContagemAnos", resposta.getBody().get("listaContagemAnos"));
		model.addAttribute("listaContagemMeses", resposta.getBody().get("listaContagemMeses"));
				
		return "presenca/presencaVizualizar";
	}
}
