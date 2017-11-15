package com.gambino_serra.condomanager_amministratore.Model.Entity;


public class CardRapportoIntervento {

    private String idRapportoIntervento;
    private String data;
    private String notaAmministratore;
    private String notaFornitore;
    private String ticketIntervento;
    private String foto;
    private String letto;

    public CardRapportoIntervento(String idRapportoIntervento, String data, String notaAmministratore, String notaFornitore, String ticketIntervento, String foto, String letto) {
        this.idRapportoIntervento = idRapportoIntervento;
        this.data = data;
        this.notaAmministratore = notaAmministratore;
        this.notaFornitore = notaFornitore;
        this.ticketIntervento = ticketIntervento;
        this.foto = foto;
        this.letto = letto;
    }

    public String getIdRapportoIntervento() {
        return idRapportoIntervento;
    }

    public void setIdRapportoIntervento(String idRapportoIntervento) {
        this.idRapportoIntervento = idRapportoIntervento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNotaAmministratore() {
        return notaAmministratore;
    }

    public void setNotaAmministratore(String notaAmministratore) {
        this.notaAmministratore = notaAmministratore;
    }

    public String getNotaFornitore() {
        return notaFornitore;
    }

    public void setNotaFornitore(String notaFornitore) {
        this.notaFornitore = notaFornitore;
    }

    public String getTicketIntervento() {
        return ticketIntervento;
    }

    public void setTicketIntervento(String ticketIntervento) {
        this.ticketIntervento = ticketIntervento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLetto() {
        return letto;
    }

    public void setLetto(String letto) {
        this.letto = letto;
    }
}