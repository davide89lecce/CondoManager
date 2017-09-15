package com.gambino_serra.condomanager_amministratore.Model.Entity;

public class Amministratore {
    private String uidAmministratore;
    private String nome;
    private String codicefiscale;
    private String studio;
    private String sede;
    private String email;
    private String telefono;

    public Amministratore(String uidAmministratore,
                          String nome,
                          String codicefiscale,
                          String studio,
                          String sede,
                          String email,
                          String telefono) {

            this.uidAmministratore = uidAmministratore;
            this.nome = nome;
            this.codicefiscale = codicefiscale;
            this.studio = studio;
            this.sede = sede;
            this.email = email;
            this.telefono = telefono;
            }

    public String getUidAmministratore() {
        return uidAmministratore;
    }

    public void setUidAmministratore(String uidAmministratore) {
        this.uidAmministratore = uidAmministratore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
