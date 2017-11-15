package com.gambino_serra.condomanager_amministratore.Model.Entity;


import java.util.Map;

public class SondaggioSceltaSM {
    private String idSondaggio;
    private String stabile;
    private String tipologia;
    private String oggetto;
    private String descrizione;
    private Map<String, Object> sondaggioMap2;
    private Map<String, Object> sondaggioMap3;
    private String data;
    private String stato;

    public SondaggioSceltaSM(String idSondaggio, String stabile, String tipologia, String oggetto, String descrizione, String data, String stato) {
        this.idSondaggio = idSondaggio;
        this.stabile = stabile;
        this.tipologia = tipologia;
        this.oggetto = oggetto;
        this.descrizione = descrizione;
        this.data = data;
        this.stato = stato;
    }

    public SondaggioSceltaSM(String idSondaggio, String stabile, String tipologia, String oggetto, String descrizione, Map<String, Object> sondaggioMap2, Map<String, Object> sondaggioMap3, String data, String stato) {
        this.idSondaggio = idSondaggio;
        this.stabile = stabile;
        this.tipologia = tipologia;
        this.oggetto = oggetto;
        this.descrizione = descrizione;
        this.sondaggioMap2 = sondaggioMap2;
        this.sondaggioMap3 = sondaggioMap3;
        this.data = data;
        this.stato = stato;
    }

    public String getIdSondaggio() {
        return idSondaggio;
    }

    public void setIdSondaggio(String idSondaggio) {
        this.idSondaggio = idSondaggio;
    }

    public String getStabile() {
        return stabile;
    }

    public void setStabile(String stabile) {
        this.stabile = stabile;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
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

    public Map<String, Object> getSondaggioMap2() {
        return sondaggioMap2;
    }

    public void setSondaggioMap2(Map<String, Object> sondaggioMap2) {
        this.sondaggioMap2 = sondaggioMap2;
    }

    public Map<String, Object> getSondaggioMap3() {
        return sondaggioMap3;
    }

    public void setSondaggioMap3(Map<String, Object> sondaggioMap3) {
        this.sondaggioMap3 = sondaggioMap3;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
