package com.futebol.gestao_time.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.model.Usuario;
import com.futebol.gestao_time.repository.IUsuario;
import com.futebol.gestao_time.utils.Resposta;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario repository;

    public Resposta buscaTodosAtivos() {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        List<Usuario> usuarios = repository.findByAtivo(true);

        if (!usuarios.isEmpty()) {
            resposta.setStatus(HttpStatus.OK);
            body.put("usuarios", usuarios);
            resposta.setBody(body);
            return resposta;
        }

        resposta.setStatus(HttpStatus.NOT_FOUND);
        resposta.setError("Usuarios não encontrado.");
        return resposta;
    }

    public Resposta contaAtivos() {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();

        resposta.setStatus(HttpStatus.OK);
        body.put("ativos", repository.countAtivos());
        resposta.setBody(body);

        return resposta;
    }

    public Resposta contaInativos() {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();

        resposta.setStatus(HttpStatus.OK);
        body.put("inativos", repository.countInativos());
        resposta.setBody(body);

        return resposta;
    }
}
