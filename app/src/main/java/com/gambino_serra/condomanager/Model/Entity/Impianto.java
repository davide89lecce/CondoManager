package com.gambino_serra.condomanager.Model.Entity;

/**
 * Created by condomanager on 13/02/17.
 */

public class Impianto {

    private Integer idImpianto;
    private String impianto;
    private String descrizione;
    private String note;
    private Integer condominio;
    private String fornitore;

    public Impianto(Integer idImpianto, String impianto, String descrizione, String note, Integer condominio, String fornitore) {
        this.idImpianto = idImpianto;
        this.impianto = impianto;
        this.descrizione = descrizione;
        this.note = note;
        this.condominio = condominio;
        this.fornitore = fornitore;
    }

    public Integer getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(Integer idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getImpianto() {
        return impianto;
    }

    public void setImpianto(String impianto) {
        this.impianto = impianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCondominio() {
        return condominio;
    }

    public void setCondominio(Integer condominio) {
        this.condominio = condominio;
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }
}
