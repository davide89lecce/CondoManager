package com.gambino_serra.condomanager_amministratore.Model.Entity;



public class CardTicketIntervento implements Comparable<CardTicketIntervento> {

    private String idTicketIntervento;
    private String idStabile;
    private String oggetto;
    private String priorità;
    private String stato;
    private String descrizioneCondomini;
    private String aggiornamentoCondomini;
    private String dataTicket;
    private String dataUltimoAggiornamento;
    private String numeroAggiornamenti;

    public CardTicketIntervento(String idTicketIntervento,
                                 String idStabile,
                                 String oggetto,
                                 String priorità,
                                 String stato,
                                 String descrizioneCondomini,
                                 String aggiornamentoCondomini,
                                 String dataTicket,
                                 String dataUltimoAggiornamento,
                                String numeroAggiornamenti) {


        this.idTicketIntervento = idTicketIntervento;
        this.idStabile = idStabile;
        this.oggetto = oggetto;
        this.priorità = priorità;
        this.stato = stato;
        this.descrizioneCondomini = descrizioneCondomini;
        this.aggiornamentoCondomini = aggiornamentoCondomini;
        this.dataTicket = dataTicket;
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
        this.numeroAggiornamenti = numeroAggiornamenti;
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

    public String getDescrizioneCondomini() {
        return descrizioneCondomini;
    }

    public void setDescrizioneCondomini(String descrizioneCondomini) {
        this.descrizioneCondomini = descrizioneCondomini;
    }

    public String getAggiornamentoCondomini() {
        return aggiornamentoCondomini;
    }

    public void setAggiornamentoCondomini(String aggiornamentoCondomini) {
        this.aggiornamentoCondomini = aggiornamentoCondomini;
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

    public String getNumeroAggiornamenti() {
        return numeroAggiornamenti;
    }

    public void setNumeroAggiornamenti(String numeroAggiornamenti) {
        this.numeroAggiornamenti = numeroAggiornamenti;
    }

    // Metodo per confrontare le card di intervento e dare la precedenza agli interventi con priorità più alta
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
