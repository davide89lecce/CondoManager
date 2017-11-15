package com.gambino_serra.condomanager_amministratore.Model.Entity;


public class MarkerIntervento {

    private String idTicketIntervento;
    private String dataTicket;
    private String oggetto;
    private String richiesta;
    private String stabile;
    private String stato;
    private String priorità;
    private String latitudine;
    private String longitudine;
    private String nomeStabile;
    private String indirizzoStabile;

    public MarkerIntervento(String idTicketIntervento, String dataTicket, String oggetto, String richiesta, String stabile, String stato, String priorità, String latitudine, String longitudine, String nomeStabile, String indirizzoStabile) {
        this.idTicketIntervento = idTicketIntervento;
        this.dataTicket = dataTicket;
        this.oggetto = oggetto;
        this.richiesta = richiesta;
        this.stabile = stabile;
        this.stato = stato;
        this.priorità = priorità;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nomeStabile = nomeStabile;
        this.indirizzoStabile = indirizzoStabile;
    }

    public String getIdTicketIntervento() {
        return idTicketIntervento;
    }

    public void setIdTicketIntervento(String idTicketIntervento) {
        this.idTicketIntervento = idTicketIntervento;
    }

    public String getDataTicket() {
        return dataTicket;
    }

    public void setDataTicket(String dataTicket) {
        this.dataTicket = dataTicket;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getRichiesta() {
        return richiesta;
    }

    public void setRichiesta(String richiesta) {
        this.richiesta = richiesta;
    }

    public String getStabile() {
        return stabile;
    }

    public void setStabile(String stabile) {
        this.stabile = stabile;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getPriorità() {
        return priorità;
    }

    public void setPriorità(String priorità) {
        this.priorità = priorità;
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

    public String getNomeStabile() {
        return nomeStabile;
    }

    public void setNomeStabile(String nomeStabile) {
        this.nomeStabile = nomeStabile;
    }

    public String getIndirizzoStabile() {
        return indirizzoStabile;
    }

    public void setIndirizzoStabile(String indirizzoStabile) {
        this.indirizzoStabile = indirizzoStabile;
    }
}
