package com.futebol.gestao_time.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebol.gestao_time.DTO.UsuarioDTO;
import com.futebol.gestao_time.service.LogUsuarioService;
import com.futebol.gestao_time.service.UsuarioComplementoService;
import com.futebol.gestao_time.utils.Resposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("usuario")
public class UsuarioComplementoController {

    @Autowired
    private UsuarioComplementoService service;

    @Autowired
    private LogUsuarioService logUsuarioService;

    @PostMapping("/atualiza-atatus")
    public ResponseEntity<Object> salvarLogPresenca(@RequestBody UsuarioDTO dto) {
        Resposta resposta = new Resposta();

        resposta = service.atualizaStatus(dto);

        if (resposta.getStatus() == HttpStatus.OK) {
            if (!logUsuarioService.salvarLogUsuario(dto)) {
                System.err.println("Houve um problema ao registrar o log presença:\npessoa -> "
                        + dto.pessoa() + "\nusuario -> " + dto.usuario()
                        + "\njustificativa -> " + dto.justificativa()
                        + "\nstatus -> " + dto.status());
            }
        }

        return ResponseEntity.status(resposta.getStatus()).body(resposta.getBody().get("body"));
    }

}
