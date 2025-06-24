package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.entities.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClientResource {

    @GetMapping
    public ResponseEntity<Client> findAll(){
        Client client = new Client(1L, "Anna", "email", "senha", "login", "celular", "endereco");
        return ResponseEntity.ok().body(client);
    }

}
