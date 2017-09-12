package com.gambino_serra.condomanager_amministratore.Model.Entity;

public class ListStabile {

    private String idStabile;
    private String nomeStabile;
    private String indirizzo;

    public ListStabile(String idStabile, String nomeStabile, String indirizzo) {
        this.idStabile = idStabile;
        this.nomeStabile = nomeStabile;
        this.indirizzo = indirizzo;
    }

    public String getIdStabile() {
        return idStabile;
    }

    public void setIdStabile(String idStabile) {
        this.idStabile = idStabile;
    }

    public String getNomeStabile() {
        return nomeStabile;
    }

    public void setNomeStabile(String nomeStabile) {
        this.nomeStabile = nomeStabile;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
