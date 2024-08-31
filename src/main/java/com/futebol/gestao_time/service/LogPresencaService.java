package com.futebol.gestao_time.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.DTO.PresencaDTO;
import com.futebol.gestao_time.model.LogPresenca;
import com.futebol.gestao_time.repository.ILogPresenca;
import com.futebol.gestao_time.repository.IPresenca;
import com.futebol.gestao_time.repository.IUsuarioComplemento;

@Service
public class LogPresencaService {

    @Autowired
    private ILogPresenca repository;
    
    @Autowired
    private IPresenca presencaRepository;

    @Autowired
    private IUsuarioComplemento usuariosRepository;

    public Boolean salvarLogPresenca(PresencaDTO dto) {
        LogPresenca novo = new LogPresenca();
        
        novo.setPessoa(usuariosRepository.findById(dto.idPessoa()).get());
        novo.setUsuario(usuariosRepository.findById(dto.idUsuario()).get());
        novo.setPresenca(presencaRepository.findById(dto.idPresenca()).get());

        try {
            repository.save(novo);
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar a entidade " + e);
            return false;
        }
    }

    // public Resposta novoLogPresenca(LogPresencaDTO dto) {
    //     Resposta resposta = new Resposta();
    //     Map<String, Object> body = new HashMap<>();
    //     LogPresencas novo = new LogPresencas();
        
    //     novo.setPessoa(usuariosRepository.findById(dto.idPessoa()).get());
    //     novo.setUsuario(usuariosRepository.findById(dto.idUsuario()).get());

    //     body.put("body", repository.save(novo));

    //     if(body.get("body") != null){
    //         resposta.setStatus(HttpStatus.OK);
    //         resposta.setBody(body);

    //         Usuario update = usuariosRepository.findById(dto.idPessoa()).get();
    //         // update.setAtivo(dto.status());
    //         usuariosRepository.save(update);

    //         return resposta;
    //     }

    //     resposta.setStatus(HttpStatus.BAD_REQUEST);
    //     resposta.setMensagem("Houve um erro ao salvar o log");

    //     return resposta;
    // }

}
