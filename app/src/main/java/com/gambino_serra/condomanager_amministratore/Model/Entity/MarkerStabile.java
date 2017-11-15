package com.gambino_serra.condomanager_amministratore.Model.Entity;

public class MarkerStabile {

    private String idStabile;
    private String nomeStabile;
    private String indirizzo;
    private String latitudine;
    private String longitudine;

    public MarkerStabile(String idStabile, String nomeStabile, String indirizzo, String latitudine, String longitudine) {
        this.idStabile = idStabile;
        this.nomeStabile = nomeStabile;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
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

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }
}
