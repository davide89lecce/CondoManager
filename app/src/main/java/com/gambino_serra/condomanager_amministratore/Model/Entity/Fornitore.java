package com.gambino_serra.condomanager_amministratore.Model.Entity;

/**
 * Created by condomanager_amministratore on 13/02/17.
 */

public class Fornitore {

    private String username;
    private String azienda;
    private String indirizzo;
    private String telefono;
    private String email;
    private String partitaIva;
    private String responsabile;

    public Fornitore(String username, String azienda, String indirizzo, String telefono, String email, String partitaIva, String responsabile) {
        this.username = username;
        this.azienda = azienda;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.partitaIva = partitaIva;
        this.responsabile = responsabile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

}
