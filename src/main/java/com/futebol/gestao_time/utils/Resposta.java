package com.futebol.gestao_time.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Resposta {
    private Map<String, Object> body;
    private HttpStatus status;
    private String error;
    private String mensagem;
}