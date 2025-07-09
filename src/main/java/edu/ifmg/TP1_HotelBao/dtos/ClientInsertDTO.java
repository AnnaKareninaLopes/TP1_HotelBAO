package edu.ifmg.TP1_HotelBao.dtos;

import jakarta.validation.constraints.Size;

public class ClientInsertDTO extends ClientDTO {

    @Size(min = 2, max = 64)
    private String password;

    public ClientInsertDTO() {
        super();
    }

    public ClientInsertDTO(ClientDTO client) {
        this.setId(client.getId());
        this.setUsername(client.getUsername());
        this.setEmail(client.getEmail());
        this.setLogin(client.getLogin());
        this.setCelular(client.getCelular());
        this.setRoles(client.getRoles());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
