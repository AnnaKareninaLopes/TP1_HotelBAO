package edu.ifmg.TP1_HotelBao.dtos;

import java.util.List;

public class InvoiceDTO {
    private Long clientId;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private List<InvoiceStayDTO> estadias;
    private Double total;

    public InvoiceDTO() {}

    public InvoiceDTO(Long clientId, String nome, String endereco, String telefone, String email, List<InvoiceStayDTO> estadias, Double total) {
        this.clientId = clientId;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.estadias = estadias;
        this.total = total;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<InvoiceStayDTO> getEstadias() {
        return estadias;
    }

    public void setEstadias(List<InvoiceStayDTO> estadias) {
        this.estadias = estadias;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
