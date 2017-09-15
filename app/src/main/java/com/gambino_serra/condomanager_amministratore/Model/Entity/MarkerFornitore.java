package com.gambino_serra.condomanager_amministratore.Model.Entity;

public class MarkerFornitore {

    private String idFornitore;
    private String nomeFornitore;
    private String indirizzo;
    private String latitudine;
    private String longitudine;

    public MarkerFornitore(String idFornitore, String nomeFornitore, String indirizzo, String latitudine, String longitudine) {
        this.idFornitore = idFornitore;
        this.nomeFornitore = nomeFornitore;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getIdFornitore() {
        return idFornitore;
    }

    public void setIdFornitore(String idFornitore) {
        this.idFornitore = idFornitore;
    }

    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
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
