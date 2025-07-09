package edu.ifmg.TP1_HotelBao.resources;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("usuario", "Anna");
        return "home";
    }

    @GetMapping("/clientes")
    public String listarClientes() {
        return "cliente-list";
    }

}
