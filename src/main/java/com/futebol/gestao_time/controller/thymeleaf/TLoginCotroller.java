package com.futebol.gestao_time.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TLoginCotroller {

    @GetMapping({ "/", "/login" })
    public String login(Model model) {
        return "login/login";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        return "login/registro";
    }

    @GetMapping("/recupera-senha")
    public String recuperar(Model model) {
        return "login/recuperaSenha";
    }

    @GetMapping("/troca-senha")
    public String trocar(Model model) {
        return "login/esqueceuSenha";
    }

}
