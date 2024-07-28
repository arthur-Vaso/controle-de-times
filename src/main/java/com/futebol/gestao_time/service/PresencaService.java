package com.futebol.gestao_time.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.Functions.ContagemDias;
import com.futebol.gestao_time.model.Presenca;
import com.futebol.gestao_time.model.Usuario;
import com.futebol.gestao_time.repository.IPresencaRepository;
import com.futebol.gestao_time.repository.IUsuarioRepository;
import com.futebol.gestao_time.utils.Mes;
import com.futebol.gestao_time.utils.Resposta;
import com.futebol.gestao_time.view.ContagemMesAno;
import com.futebol.gestao_time.view.PresencaContaAnoMesView;
import com.futebol.gestao_time.view.PresencaContaAnoView;

import jakarta.persistence.Tuple;

@Service
public class PresencaService {

    @Autowired
    private IPresencaRepository repository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Resposta bucarPresencaPorIdUsuario(Integer id) {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();

        List<Presenca> presencas = repository.buscaTodosUniaoUsuario(id);
        Set<Integer> meses = new HashSet<>();
        Set<String> anos = new HashSet<>();

        for (Presenca presenca : presencas) {
            meses.add(presenca.getMes());
            anos.add(presenca.getAno());
        }
        List<Integer> listaMeses = new ArrayList<>(meses);
        Collections.sort(listaMeses);
        Set<Integer> mesesOrdenados = new LinkedHashSet<>(listaMeses);
        List<String> listaAnos = new ArrayList<>(anos);
        listaAnos.sort(Collections.reverseOrder());
        System.out.println(
                "meses - " + meses + "\n lista meses - " + listaMeses + "\n meses ordenados - " + mesesOrdenados);

        resposta.setStatus(HttpStatus.OK);
        body.put("presencas", presencas);
        body.put("meses", mesesOrdenados);
        body.put("anosPresenca", listaAnos);
        resposta.setBody(body);

        return resposta;
    }

    public Resposta buscarPorAtivos() {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        List<Presenca> listaPresencas = repository.buscaOrdenadoPorAnoEMes();
        List<Usuario> listaUsuarios = usuarioRepository.findByAtivo(true);
        Set<String> nomes = new HashSet<>();
        Set<String> anos = new HashSet<>();

        if (listaPresencas == null) {
            resposta.setStatus(HttpStatus.NOT_FOUND);
            resposta.setMensagem("Presença não encontrada");
            return resposta;
        }
        System.out.println(listaPresencas.size());

        for (Presenca p : listaPresencas) {
            anos.add(p.getAno());
            for (Usuario u : listaUsuarios) {
                if (p.getUsuario().getId() == u.getId()) {
                    nomes.add(u.getNome() + " " + u.getSobrenome());
                }
            }
        }

        resposta.setStatus(HttpStatus.OK);
        body.put("body", listaPresencas);
        body.put("nomes", nomes);
        body.put("anos", anos);
        resposta.setBody(body);
        return resposta;
    }

    public Resposta buscaPresencaoUsuario(Integer id) {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        List<Presenca> listaPresencas = repository.findByIdUsuarioOderByAnoDescMes(id);

        if (listaPresencas == null) {
            resposta.setStatus(HttpStatus.NOT_FOUND);
            resposta.setMensagem("Presença não encontrada");
            return resposta;
        }

        Usuario usuario = usuarioRepository.findById(id).get();
        Set<String> anos = new HashSet<>();
        List<Tuple> contaAnosMeses = repository.countByIdUsuarioAndAnoAndMes(id);
        List<PresencaContaAnoMesView> listaAnosMeses = new ArrayList<>();
        List<Tuple> contaAnos = repository.countByIdUsuarioAndAno(id);
        List<PresencaContaAnoView> listaAnos = new ArrayList<>();
        List<ContagemMesAno> listaContagemAnos = new ArrayList();
        List<ContagemMesAno> listaContagemMeses = new ArrayList();

        for (Tuple tuple : contaAnosMeses) {
            Usuario usuarioView = (Usuario) tuple.get("usuario");
            Integer mes = (Integer) tuple.get("mes");
            String ano = (String) tuple.get("ano");
            Integer conta = ((Long) tuple.get("conta")).intValue();

            PresencaContaAnoMesView view = new PresencaContaAnoMesView(usuarioView, mes, ano, conta);
            listaAnosMeses.add(view);
        }

        for (Tuple tuple : contaAnos) {
            Usuario usuarioView = (Usuario) tuple.get("usuario");
            String ano = (String) tuple.get("ano");
            Integer conta = ((Long) tuple.get("conta")).intValue();

            PresencaContaAnoView view = new PresencaContaAnoView(usuarioView, ano, conta);
            listaAnos.add(view);
        }

        for (Presenca p : listaPresencas) {
            anos.add(p.getAno());
        }

        for (String ano : anos) {
            ContagemMesAno contagemAno = new ContagemMesAno();
            contagemAno.setAnoReferencia(ano);
            contagemAno.setMesReferencia(-1);
            contagemAno.setContagem(ContagemDias.contarSábadosNoAno(Integer.parseInt(ano)));
            listaContagemAnos.add(contagemAno);
            for (Mes mes : Mes.values()) {
                ContagemMesAno contagemMes = new ContagemMesAno();
                contagemMes.setAnoReferencia(ano);
                contagemMes.setMesReferencia(mes.getNumero());
                contagemMes.setContagem(ContagemDias.contarSabadosNoMes(Integer.parseInt(ano), mes.getNumero())) ;
                listaContagemMeses.add(contagemMes);
            }
        }
        for(Tuple c : contaAnosMeses){
            System.out.println(c);
        }
        for(PresencaContaAnoMesView c : listaAnosMeses){
            System.out.println(c.getMes() + "<--->" + c.getConta());
        }
        resposta.setStatus(HttpStatus.OK);
        body.put("body", listaPresencas);
        body.put("usuario", usuario);
        body.put("anos", anos);
        body.put("listaAnosMeses", listaAnosMeses);
        body.put("listaAnos", listaAnos);
        body.put("listaContagemAnos", listaContagemAnos);
        body.put("listaContagemMeses", listaContagemMeses);
        resposta.setBody(body);

        return resposta;
    }

}
