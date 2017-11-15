package com.gambino_serra.condomanager_amministratore.Model.Entity;


public class Messaggio {

    private String id;
    private String idCondomino;
    private String idStabile;
    private String tipologia;
    private String messaggio;
    private String data;
    private String foto;
    private String nomeCondomino;
    private String nomeStabile;
    private String letto;

    public Messaggio (){}

    public Messaggio(String id, String idCondomino, String idStabile, String tipologia, String messaggio, String data, String foto, String nomeCondomino, String nomeStabile, String letto) {
        this.id = id;
        this.idCondomino = idCondomino;
        this.idStabile = idStabile;
        this.tipologia = tipologia;
        this.messaggio = messaggio;
        this.data = data;
        this.foto = foto;
        this.nomeCondomino = nomeCondomino;
        this.nomeStabile = nomeStabile;
        this.letto = letto;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCondomino() {
        return idCondomino;
    }

    public void setIdCondomino(String idCondomino) {
        this.idCondomino = idCondomino;
    }

    public String getIdStabile() {
        return idStabile;
    }

    public void setIdStabile(String idStabile) {
        this.idStabile = idStabile;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNomeCondomino() {
        return nomeCondomino;
    }

    public void setNomeCondomino(String nomeCondomino) {
        this.nomeCondomino = nomeCondomino;
    }

    public String getNomeStabile() {
        return nomeStabile;
    }

    public void setNomeStabile(String nomeStabile) {
        this.nomeStabile = nomeStabile;
    }

    public String getLetto() {
        return letto;
    }

    public void setLetto(String letto) {
        this.letto = letto;
    }
}
