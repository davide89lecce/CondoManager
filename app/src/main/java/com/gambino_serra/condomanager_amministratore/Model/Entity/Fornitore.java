package com.gambino_serra.condomanager_amministratore.Model.Entity;



public class Fornitore {
    private String uid;
    private String nome;
    private String nome_azienda;
    private String categoria;
    private String partita_iva;
    private String telefono;
    private String indirizzo;
    private String email;

    public Fornitore(String uid, String nome, String nome_azienda, String categoria, String partita_iva, String telefono, String indirizzo, String email) {
        this.uid = uid;
        this.nome = nome;
        this.nome_azienda = nome_azienda;
        this.categoria = categoria;
        this.partita_iva = partita_iva;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_azienda() {
        return nome_azienda;
    }

    public void setNome_azienda(String nome_azienda) {
        this.nome_azienda = nome_azienda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPartita_iva() {
        return partita_iva;
    }

    public void setPartita_iva(String partita_iva) {
        this.partita_iva = partita_iva;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

