package com.futebol.gestao_time.controller;

import org.springframework.web.bind.annotation.RestController;

import com.futebol.gestao_time.DTO.PresencaDTO;
import com.futebol.gestao_time.model.Presenca;
import com.futebol.gestao_time.service.LogPresencaService;
import com.futebol.gestao_time.service.PresencaService;
import com.futebol.gestao_time.utils.Resposta;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("presenca")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @Autowired
    private LogPresencaService logPresencaService;

    @PostMapping("/salvar-presenca/")
    public ResponseEntity<Object> salvarPresenca(@RequestBody Presenca presenca,
            @RequestParam("id") Integer id,
            @RequestParam("idUsuario") Integer idUsuario) {
        Resposta resposta = new Resposta();

        resposta = presencaService.salvarPresenca(id, presenca);

        Presenca presencaSalva = (Presenca) resposta.getBody().get("body");
        if (resposta.getStatus() == HttpStatus.OK) {
            PresencaDTO dto = new PresencaDTO(presencaSalva.getId(), id, idUsuario);
            if (!logPresencaService.salvarLogPresenca(dto)) {
                System.err.println("Houve um problema ao registrar o log presença:\npessoa -> "
                        + id + "\nusuario -> " + idUsuario);
            }
        }

        return ResponseEntity.status(resposta.getStatus()).body(resposta.getBody().get("body"));
    }

}
