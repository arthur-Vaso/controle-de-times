package com.futebol.gestao_time.DTO;

public record UsuarioDTO(
    Integer pessoa,
    Integer usuario,
    String justificativa,
    boolean status
) {

}
