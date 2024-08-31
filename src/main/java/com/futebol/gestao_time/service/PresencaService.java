package com.futebol.gestao_time.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.Functions.ContagemDias;
import com.futebol.gestao_time.model.Presenca;
import com.futebol.gestao_time.model.UsuarioComplemento;
import com.futebol.gestao_time.repository.IPresenca;
import com.futebol.gestao_time.repository.IUsuarioComplemento;
import com.futebol.gestao_time.utils.Mes;
import com.futebol.gestao_time.utils.Resposta;
import com.futebol.gestao_time.view.ContagemMesAno;
import com.futebol.gestao_time.view.PresencaContaAnoMesView;
import com.futebol.gestao_time.view.PresencaContaAnoView;

import jakarta.persistence.Tuple;

@Service
public class PresencaService {

    @Autowired
    private IPresenca repository;

    @Autowired
    private IUsuarioComplemento usuarioRepository;

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

        resposta.setStatus(HttpStatus.OK);
        body.put("presencas", presencas);
        body.put("meses", mesesOrdenados);
        body.put("anosPresenca", listaAnos);
        resposta.setBody(body);

        return resposta;
    }

    // Inutil
    public List<Presenca> buscarPorAtivos() {
        List<UsuarioComplemento> listaAtivos = usuarioRepository.findByAtivo(true);
        List<Presenca> presencas = new ArrayList<>();
        // System.out.println(repository.findAll());
        for (UsuarioComplemento uc : listaAtivos) {
            presencas.add(repository.findByIdUsuario(uc.getId()));
        }

        return presencas;
    }

    // Inutil
    public Resposta buscaPorAtivos() {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        List<Presenca> listaPresencas = repository.buscaOrdenadoPorAnoEMes();
        List<UsuarioComplemento> listaUsuarios = usuarioRepository.findByAtivo(true);
        Set<String> nomes = new HashSet<>();
        Set<String> anos = new HashSet<>();

        if (listaPresencas == null) {
            resposta.setStatus(HttpStatus.NOT_FOUND);
            resposta.setMensagem("Presença não encontrada");
            return resposta;
        }

        for (Presenca p : listaPresencas) {
            anos.add(p.getAno());
            for (UsuarioComplemento u : listaUsuarios) {
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

    public List<Presenca> buscaPorUsuarioOrdenadoPorAnoDecEMes(Integer id) {
        return repository.findByIdUsuarioOderByAnoDescMes(id);
    }

    public Set<String> listarAnos() {
        List<Presenca> presencasLista = repository.findAll();
        Set<String> anos = new HashSet<>();

        for (Presenca p : presencasLista) {
            anos.add(p.getAno());
        }

        return anos;
    }

    public Set<String> listarAnos(List<Presenca> lista) {
        Set<String> anos = new HashSet<>();

        for (Presenca p : lista) {
            anos.add(p.getAno());
        }

        return anos;
    }

    public List<PresencaContaAnoMesView> listarAnosEMeses(Integer id) {
        List<Tuple> contaAnosMeses = repository.countByIdUsuarioAndAnoAndMes(id);
        List<PresencaContaAnoMesView> listaAnosMeses = new ArrayList<>();

        for (Tuple tuple : contaAnosMeses) {
            UsuarioComplemento usuarioView = (UsuarioComplemento) tuple.get("usuario");
            Integer mes = (Integer) tuple.get("mes");
            String ano = (String) tuple.get("ano");
            Integer conta = ((Long) tuple.get("conta")).intValue();

            PresencaContaAnoMesView view = new PresencaContaAnoMesView(usuarioView, mes, ano, conta);
            listaAnosMeses.add(view);
        }

        return listaAnosMeses;
    }

    public List<PresencaContaAnoView> listarAnos(Integer id) {
        List<Tuple> contaAnos = repository.countByIdUsuarioAndAno(id);
        List<PresencaContaAnoView> listaAnos = new ArrayList<>();

        for (Tuple tuple : contaAnos) {
            UsuarioComplemento usuarioView = (UsuarioComplemento) tuple.get("usuario");
            String ano = (String) tuple.get("ano");
            Integer conta = ((Long) tuple.get("conta")).intValue();

            PresencaContaAnoView view = new PresencaContaAnoView(usuarioView, ano, conta);
            listaAnos.add(view);
        }

        return listaAnos;
    }

    public List<ContagemMesAno> contarAnos(Set<String> anos) {
        List<ContagemMesAno> listaContagemAnos = new ArrayList<>();

        for (String ano : anos) {
            ContagemMesAno contagemAno = new ContagemMesAno();
            contagemAno.setAnoReferencia(ano);
            contagemAno.setMesReferencia(-1);
            contagemAno.setContagem(ContagemDias.contarSábadosNoAno(Integer.parseInt(ano)));
            listaContagemAnos.add(contagemAno);
        }

        return listaContagemAnos;
    }

    public List<ContagemMesAno> contarMeses(Set<String> anos) {
        List<ContagemMesAno> listaContagemMeses = new ArrayList<>();

        for (String ano : anos) {
            for (Mes mes : Mes.values()) {
                ContagemMesAno contagemMes = new ContagemMesAno();
                contagemMes.setAnoReferencia(ano);
                contagemMes.setMesReferencia(mes.getNumero());
                contagemMes.setContagem(ContagemDias.contarSabadosNoMes(Integer.parseInt(ano), mes.getNumero()));
                listaContagemMeses.add(contagemMes);
            }
        }

        return listaContagemMeses;
    }

    public Resposta salvarPresenca(Integer id, Presenca presenca) {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();

        Presenca nova = new Presenca();
        nova.setUsuario(usuarioRepository.findById(id).get());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(presenca.getPresenca());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        int mes = calendar.get(Calendar.MONTH) + 1;
        int ano = calendar.get(Calendar.YEAR);

        nova.setPresenca(calendar.getTime());
        nova.setMes(mes);
        nova.setAno(String.valueOf(ano));
        nova.setObservacao(presenca.getObservacao());

        resposta.setStatus(HttpStatus.OK);
        body.put("body", repository.save(nova));

        if (body.get("body") != null) {
            resposta.setStatus(HttpStatus.OK);
            resposta.setBody(body);

            return resposta;
        }
        resposta.setStatus(HttpStatus.BAD_REQUEST);
        resposta.setMensagem("Houve um erro ao salvar a presença");

        return resposta;
    }

    public String buscarAnoMaisAntigo() {
        return repository.buscarAnoMaisAntigo();
    }

    public String buscarAnoMaisRecente() {
        return repository.buscarAnoMaisRecente();
    }

    public List<UsuarioComplemento> buscarNomesEntrePeriodo(String ano, Integer mes) {
        List<UsuarioComplemento> usuarios = new ArrayList<>();

        List<Integer> idsUsuarios = repository.buscarIdUsuarioComplemento(mes, ano);
        System.out.println(idsUsuarios);
        if (idsUsuarios.size() > 0) {
            idsUsuarios.forEach(id -> usuarios.add(usuarioRepository.findById(id).get()));
        }
        System.out.println(usuarios);

        return usuarios;
    }

}
