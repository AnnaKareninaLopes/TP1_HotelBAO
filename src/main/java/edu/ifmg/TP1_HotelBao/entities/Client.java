package edu.ifmg.TP1_HotelBao.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremento
    private Long id;

    private String nome;
    private String email;
    @Column(unique = true)
    private String senha;
    @Column(unique = true)
    private String login;
    private String celular;
    @Column(unique = true)
    private String endereco;

    public Client() {
    }

    public Client(Long id, String nome, String email, String senha, String login, String celular, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.login = login;
        this.celular = celular;
        this.endereco = endereco;
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
    public boolean equals(Object o) {
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(senha, client.senha) && Objects.equals(login, client.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senha, login);
    }

    @Override
    public String toString() {
        return "Client{" +
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
