package com.gambino_serra.condomanager.Model.Entity;

/**
 * Created by condomanager on 13/02/17.
 */

public class Intervento {

    private Integer idIntervento;
    private String data;
    private String intervento;
    private String segnalazione;

    public Intervento(Integer idIntervento, String data, String intervento, String segnalazione) {
        this.idIntervento = idIntervento;
        this.data = data;
        this.intervento = intervento;
        this.segnalazione = segnalazione;
    }

    public Integer getIdIntervento() {
        return idIntervento;
    }

    public void setIdIntervento(Integer idIntervento) {
        this.idIntervento = idIntervento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIntervento() {
        return intervento;
    }

    public void setIntervento(String intervento) {
        this.intervento = intervento;
    }

    public String getSegnalazione() {
        return segnalazione;
    }

    public void setSegnalazione(String segnalazione) {
        this.segnalazione = segnalazione;
    }
}
