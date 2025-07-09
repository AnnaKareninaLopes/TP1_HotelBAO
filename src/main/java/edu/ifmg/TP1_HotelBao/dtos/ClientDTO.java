package edu.ifmg.TP1_HotelBao.dtos;

import edu.ifmg.TP1_HotelBao.entities.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório!")
    private String username;
    @Email(message = "E-mail inválido!")
    private String email;
    @NotBlank(message = "Campo obrigatório!")
    private String login;
    private String celular;
    private String endereco;

    private Set<RoleDTO> roles = new HashSet<>();

    public ClientDTO() {
    }

    public ClientDTO(Long id, String username, String email, String password, String login, String celular, String endereco) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.login = login;
        this.celular = celular;
        this.endereco = endereco;
    }

    public ClientDTO(Client client) {
        id = client.getId();
        username = client.getUsername();
        email = client.getEmail();
        login = client.getLogin();
        celular = client.getCelular();
        endereco = client.getEndereco();
        client.getRoles().forEach(role ->
                roles.add(new RoleDTO(role))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", nome='" + username + '\'' +
                ", email='" + email + '\'' +

                ", login='" + login + '\'' +
                ", celular='" + celular + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
