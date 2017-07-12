package com.gambino_serra.condomanager_amministratore.Model.Entity;

/**
 * Created by condomanager_amministratore on 13/02/17.
 */

public class Condomino {

    private String username;
    private String nome;
    private String cognome;
    private String condominio;

    public Condomino(String username, String nome, String cognome, String condominio) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.condominio = condominio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }
}
