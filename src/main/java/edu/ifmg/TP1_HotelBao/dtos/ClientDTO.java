package edu.ifmg.TP1_HotelBao.dtos;

import edu.ifmg.TP1_HotelBao.entities.Client;
import jakarta.persistence.Column;

public class ClientDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String login;
    private String celular;
    private String endereco;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String nome, String email, String senha, String login, String celular, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.login = login;
        this.celular = celular;
        this.endereco = endereco;
    }

    public ClientDTO(Client entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.senha = entity.getSenha();
        this.login = entity.getLogin();
        this.celular = entity.getCelular();
        this.endereco = entity.getEndereco();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", login='" + login + '\'' +
                ", celular='" + celular + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
