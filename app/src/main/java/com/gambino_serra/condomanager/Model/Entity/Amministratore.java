package com.gambino_serra.condomanager.Model.Entity;

/**
 * Created by condomanager on 13/02/17.
 */

public class Amministratore {

    private String username;
    private String studio;
    private String amministratore;
    private String sede;
    private String telefono;
    private String email;

    public Amministratore(String username, String studio, String amministratore, String sede, String telefono, String email) {
        super();
        this.username = username;
        this.studio = studio;
        this.amministratore = amministratore;
        this.sede = sede;
        this.telefono = telefono;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getStudio() {
        return studio;
    }

    public String getAmministratore() {
        return amministratore;
    }

    public String getSede() {
        return sede;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setAmministratore(String amministratore) {
        this.amministratore = amministratore;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
