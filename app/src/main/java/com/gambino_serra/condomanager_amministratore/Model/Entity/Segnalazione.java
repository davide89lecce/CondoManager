package com.gambino_serra.condomanager_amministratore.Model.Entity;

/**
 * Created by condomanager_amministratore on 13/02/17.
 */

public class Segnalazione {

    private Integer idSegnalazione;
    private String data;
    private String segnalazione;
    private String condomino;
    private Integer idCondominio;
    private Integer impianto;
    private String fornitore;
    private String stato;
    private String condominio;


    public Segnalazione(Integer idSegnalazione, String data, String segnalazione, String condomino, Integer idCondominio, Integer impianto, String fornitore, String stato, String condominio) {
        this.idSegnalazione = idSegnalazione;
        this.data = data;
        this.segnalazione = segnalazione;
        this.condomino = condomino;
        this.idCondominio = idCondominio;
        this.impianto = impianto;
        this.fornitore = fornitore;
        this.stato = stato;
        this.condominio = condominio;
    }

    public Integer getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(Integer idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSegnalazione() {
        return segnalazione;
    }

    public void setSegnalazione(String segnalazione) {
        this.segnalazione = segnalazione;
    }

    public String getCondomino() {
        return condomino;
    }

    public void setCondomino(String condomino) {
        this.condomino = condomino;
    }

    public Integer getIdCondominio() {
        return idCondominio;
    }

    public void setidCondominio(Integer idCondominio) {
        this.idCondominio = idCondominio;
    }

    public Integer getImpianto() {
        return impianto;
    }

    public void setImpianto(Integer impianto) {
        this.impianto = impianto;
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

}
