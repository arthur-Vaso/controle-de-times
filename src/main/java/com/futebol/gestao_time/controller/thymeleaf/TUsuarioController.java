package com.futebol.gestao_time.controller.thymeleaf;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.futebol.gestao_time.model.UsuarioComplemento;
import com.futebol.gestao_time.repository.IUsuarioComplemento;
import com.futebol.gestao_time.service.PagamentoService;
import com.futebol.gestao_time.service.PresencaService;
import com.futebol.gestao_time.service.UsuarioService;
import com.futebol.gestao_time.utils.Mes;
import com.futebol.gestao_time.utils.Resposta;


@Controller
@RequestMapping("/usuario")
public class TUsuarioController {

    @Autowired
	private IUsuarioComplemento usuarioRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PagamentoService pagamentoService;
	@Autowired
	private PresencaService presencaService;

    @GetMapping("/visualizar")
    public String carregaJogador(Model model, @RequestParam(required = false) Integer id) {
		Optional<UsuarioComplemento> usuario = usuarioRepository.findById(id);
		Map<String, Object> respostaPagamento = pagamentoService.buscarPagamentoPorIdUsuario(id).getBody();
		Map<String, Object> respostaPresenca = presencaService.bucarPresencaPorIdUsuario(id).getBody();

		model.addAttribute("usuario", usuario.get());
		model.addAttribute("pagamentos", respostaPagamento.get("pagamentos"));
		model.addAttribute("anospagamento", respostaPagamento.get("anosPagamento"));
		model.addAttribute("presencas", respostaPresenca.get("presencas"));
		model.addAttribute("meses", respostaPresenca.get("meses"));
		model.addAttribute("enummeses", Mes.values());
		model.addAttribute("anospresenca", respostaPresenca.get("anosPresenca"));

        return "usuario/usuario";
    }
    
	@GetMapping("/listar")
	public String home(Model model) {
		Resposta resposta = usuarioService.buscaTodosAtivos();;

		model.addAttribute("usuarios", resposta.getBody().get("usuarios"));

		return "home";
	}

	@PostMapping("/listar")
	public String homeLogin(Model model) {
		Resposta resposta = usuarioService.buscaTodosAtivos();;

		model.addAttribute("usuarios", resposta.getBody().get("usuarios"));
		return "home";
	}

}
