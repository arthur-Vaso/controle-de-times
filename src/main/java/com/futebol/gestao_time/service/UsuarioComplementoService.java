package com.futebol.gestao_time.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.DTO.UsuarioDTO;
import com.futebol.gestao_time.model.Usuario;
import com.futebol.gestao_time.model.UsuarioComplemento;
import com.futebol.gestao_time.repository.IUsuario;
import com.futebol.gestao_time.repository.IUsuarioComplemento;
import com.futebol.gestao_time.utils.Resposta;
import com.futebol.gestao_time.view.UsuarioView;

@Service
public class UsuarioComplementoService {

    @Autowired
    private IUsuarioComplemento repository;

    @Autowired
    private IUsuario usuarioRepository;

    public UsuarioView buscaPorIdUsuario(Integer id) {
        Optional<UsuarioComplemento> usuarioOptional = repository.findByIdUsuario(id);

        if (usuarioOptional.isPresent()) {
            Optional<Usuario> usuario = usuarioRepository.findById(id);

            UsuarioView usuarioView = new UsuarioView(usuarioOptional.get().getId(),
                    usuarioOptional.get().getIdUsuario().getId(), usuarioOptional.get().getNome(),
                    usuarioOptional.get().getSobrenome(), usuarioOptional.get().getTelefone(),
                    usuarioOptional.get().getEWhatshapp(), usuario.get().getAtivo());

            return usuarioView;
        }

        return new UsuarioView();
    }

    public Resposta atualizaStatus(UsuarioDTO dto) {
        Resposta resposta = new Resposta();
        Map<String, Object> body = new HashMap<>();
        Optional<UsuarioComplemento> usuario = repository.findById(dto.pessoa());

        if (usuario.isPresent()) {
            UsuarioComplemento novo = new UsuarioComplemento();
            System.out.println(dto);
            novo.setId(usuario.get().getId());
            novo.setNome(usuario.get().getNome());
            novo.setSobrenome(usuario.get().getSobrenome());
            novo.setTelefone(usuario.get().getTelefone());

            body.put("body", repository.save(novo));
            resposta.setStatus(HttpStatus.OK);
            resposta.setBody(body);

            return resposta;
        }

        resposta.setStatus(HttpStatus.BAD_REQUEST);
        resposta.setMensagem("Houve um ao atualizar usuario.");

        return resposta;
    }

    public List<UsuarioComplemento> buscarAtivos(boolean ativo) {
        return repository.findByAtivo(ativo);
    }

}
