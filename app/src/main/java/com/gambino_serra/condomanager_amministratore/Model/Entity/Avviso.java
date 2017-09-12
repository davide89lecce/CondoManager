package com.gambino_serra.condomanager_amministratore.Model.Entity;


public class Avviso {

    private String idAvviso;
    private String uidAmministratore;
    private String stabile;
    private String oggetto;
    private String descrizione;
    private String dataScadenza;



    public Avviso(String idAvviso, String uidAmministratore, String stabile, String oggetto, String descrizione, String dataScadenza) {
        this.idAvviso = idAvviso;
        this.uidAmministratore = uidAmministratore;
        this.stabile = stabile;
        this.oggetto = oggetto;
        this.descrizione = descrizione;
        this.dataScadenza = dataScadenza;
    }

    public String getIdAvviso() {
        return idAvviso;
    }

    public void setIdAvviso(String idAvviso) {
        this.idAvviso = idAvviso;
    }

    public String getUidAmministratore() {
        return uidAmministratore;
    }

    public void setUidAmministratore(String uidAmministratore) {
        this.uidAmministratore = uidAmministratore;
    }

    public String getStabile() {
        return stabile;
    }

    public void setStabile(String stabile) {
        this.stabile = stabile;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
