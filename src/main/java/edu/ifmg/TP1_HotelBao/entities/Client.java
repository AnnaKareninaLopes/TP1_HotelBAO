package edu.ifmg.TP1_HotelBao.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_client")
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremento
    private Long id;

    private String username;
    private String email;
    @Column(unique = true)
    private String password;
    @Column(unique = true)
    private String login;
    private String celular;
    @Column(unique = true)
    private String endereco;

    @OneToMany(mappedBy = "client")
    private List<Stay> stays = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_client_role",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Client() {
    }

    public Client(Long id, String username, String email, String password, String login, String celular, String endereco) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.login = login;
        this.celular = celular;
        this.endereco = endereco;
    }

    public Client(Client entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.login = entity.getLogin();
        this.celular = entity.getCelular();
        this.endereco = entity.getEndereco();
    }

    public Client(Client entity, Set<Role> roles) {
        this(entity);
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public boolean hasRole(String roleName){
        return !roles.stream().filter(r -> r.getAuthority().equals(roleName)).toList().isEmpty();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(username, client.username) && Objects.equals(email, client.email) && Objects.equals(password, client.password) && Objects.equals(login, client.login) && Objects.equals(celular, client.celular) && Objects.equals(endereco, client.endereco) && Objects.equals(stays, client.stays) && Objects.equals(roles, client.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, login, celular, endereco, stays, roles);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nome='" + username + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + password + '\'' +
                ", login='" + login + '\'' +
                ", celular='" + celular + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }

}
