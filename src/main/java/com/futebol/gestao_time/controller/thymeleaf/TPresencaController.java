package com.futebol.gestao_time.controller.thymeleaf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.futebol.gestao_time.model.Presenca;
import com.futebol.gestao_time.model.UsuarioComplemento;
import com.futebol.gestao_time.service.PresencaService;
import com.futebol.gestao_time.service.UsuarioComplementoService;
import com.futebol.gestao_time.utils.Mes;

@Controller
@RequestMapping("/presenca")
public class TPresencaController {

	@Autowired
	private PresencaService presencaService;

	@Autowired
	private UsuarioComplementoService usuarioComplementoService;

	@GetMapping("/listar")
	public String listarPresenca(Model model, @RequestParam(required = false) String ano,
			@RequestParam(required = false) Integer mes) {

		Calendar anoAtual = Calendar.getInstance();

		ano = ano == null ? String.valueOf(anoAtual.get(Calendar.YEAR)) : ano;
		mes = mes == null ? Integer.valueOf(anoAtual.get(Calendar.MONTH) + 1) : mes;

		String anoMaisAntigo = presencaService.buscarAnoMaisAntigo();
		String anoMaisRecente = presencaService.buscarAnoMaisRecente();
		List<String> anoList = new ArrayList<>();
		for (int i = Integer.parseInt(anoMaisAntigo); i <= Integer.parseInt(anoMaisRecente); i++) {
			anoList.add(String.valueOf(i));
		}

		List<UsuarioComplemento> usuarios = usuarioComplementoService.buscarAtivos(true);
		List<UsuarioComplemento> nomes = presencaService.buscarNomesEntrePeriodo(ano, mes);

		model.addAttribute("usuarios", usuarios);
		model.addAttribute("anoslista", anoList);
		model.addAttribute("nomes", nomes);
		model.addAttribute("enummeses", Mes.values());
		model.addAttribute("ano", ano);

		return "presenca/presencaListar";
	}

	@GetMapping("/visualizar")
	public String visualizarPresenca(Model model, @RequestParam(required = false) Integer id) {
		List<Presenca> presencasLista = presencaService.buscaPorUsuarioOrdenadoPorAnoDecEMes(id);
		Set<String> anosSet = presencaService.listarAnos(presencasLista);

		model.addAttribute("presencas", presencasLista);
		model.addAttribute("usuario", usuarioComplementoService.buscaPorIdUsuario(id));
		model.addAttribute("enummeses", Mes.values());
		model.addAttribute("anos", anosSet);
		model.addAttribute("listaAnosMeses", presencaService.listarAnosEMeses(id));
		model.addAttribute("listaAnos", presencaService.listarAnos(id));
		model.addAttribute("listaContagemAnos", presencaService.contarAnos(anosSet));
		model.addAttribute("listaContagemMeses", presencaService.contarMeses(anosSet));

		return "presenca/presencaVizualizar";
	}
}
