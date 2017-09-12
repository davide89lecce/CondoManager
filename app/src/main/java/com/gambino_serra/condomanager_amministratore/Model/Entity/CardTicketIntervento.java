package com.gambino_serra.condomanager_amministratore.Model.Entity;



public class CardTicketIntervento implements Comparable<CardTicketIntervento> {

    private String idTicketIntervento;
    private String idStabile;
    private String nomeStabile;
    private String indirizzoStabile;
    private String oggetto;
    private String priorità;
    private String stato;
    private String dataTicket;
    private String dataUltimoAggiornamento;

    public CardTicketIntervento(String idTicketIntervento,
                                String idStabile,
                                String nomeStabile,
                                String indirizzoStabile,
                                String oggetto,
                                String priorità,
                                String stato,
                                String dataTicket,
                                String dataUltimoAggiornamento) {


        this.idTicketIntervento = idTicketIntervento;
        this.idStabile = idStabile;
        this.nomeStabile = nomeStabile;
        this.indirizzoStabile = indirizzoStabile;
        this.oggetto = oggetto;
        this.priorità = priorità;
        this.stato = stato;
        this.dataTicket = dataTicket;
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    public String getIdTicketIntervento() {
        return idTicketIntervento;
    }

    public void setIdTicketIntervento(String idTicketIntervento) {
        this.idTicketIntervento = idTicketIntervento;
    }

    public String getIdStabile() {
        return idStabile;
    }

    public void setIdStabile(String idStabile) {
        this.idStabile = idStabile;
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

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getPriorità() {
        return priorità;
    }

    public void setPriorità(String priorità) {
        this.priorità = priorità;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getDataTicket() {
        return dataTicket;
    }

    public void setDataTicket(String dataTicket) {
        this.dataTicket = dataTicket;
    }

    public String getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    public void setDataUltimoAggiornamento(String dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    @Override
    public int compareTo(CardTicketIntervento cardTicketIntervento) {
        //write code here for compare name

        if(Integer.parseInt(this.getPriorità()) < Integer.parseInt(cardTicketIntervento.getPriorità())) {
            return - 1;

        }else if(Integer.parseInt(this.getPriorità()) > Integer.parseInt(cardTicketIntervento.getPriorità())) {
            return  1;

        }else return 0;

    }

}
