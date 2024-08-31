package com.futebol.gestao_time.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebol.gestao_time.DTO.UsuarioDTO;
import com.futebol.gestao_time.model.LogUsuario;
import com.futebol.gestao_time.repository.ILogUsuario;
import com.futebol.gestao_time.repository.IUsuarioComplemento;

@Service
public class LogUsuarioService {

@Autowired
private ILogUsuario repository;

@Autowired
private IUsuarioComplemento usuariosRepository;

    public Boolean salvarLogUsuario(UsuarioDTO dto) {
        LogUsuario novo = new LogUsuario();
        
        novo.setPessoa(usuariosRepository.findById(dto.pessoa()).get());
        novo.setUsuario(usuariosRepository.findById(dto.usuario()).get());
        novo.setJsutificativa(dto.justificativa());
        novo.setStatus(dto.status());

        try {
            repository.save(novo);
            
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar a entidade " + e);
            return false;
        }
    }
}
