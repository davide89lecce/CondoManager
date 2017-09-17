package com.gambino_serra.condomanager_amministratore.Model.Entity;


public class TicketIntervento {

    private String idTicketIntervento;
    private String uidAmministratore;
    private String dataTicket;
    private String dataUltimoAggiornamento;
    private String fornitore;
    private String messaggioCondomino;
    private String aggiornamentoCondomini;
    private String descrizioneCondomini;
    private String oggetto;
    private String rapportiIntervento;
    private String richiesta;
    private String stabile;
    private String stato;
    private String priorità;
    private String foto;
    private String nomeAmministratore;
    private String nomeStabile;
    private String indirizzoStabile;

    public TicketIntervento(String idTicketIntervento,
                            String uidAmministratore,
                            String dataTicket,
                            String dataUltimoAggiornamento,
                            String fornitore,
                            String messaggioCondomino,
                            String aggiornamentoCondomini,
                            String descrizioneCondomini,
                            String oggetto,
                            String rapportiIntervento,
                            String richiesta,
                            String stabile,
                            String stato,
                            String priorità,
                            String foto,
                            String nomeAmministratore,
                            String nomeStabile,
                            String indirizzoStabile

    ) {

        this.idTicketIntervento = idTicketIntervento;
        this.uidAmministratore = uidAmministratore;
        this.dataTicket = dataTicket;
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
        this.fornitore = fornitore;
        this.messaggioCondomino = messaggioCondomino;
        this.aggiornamentoCondomini = aggiornamentoCondomini;
        this.descrizioneCondomini = descrizioneCondomini;
        this.oggetto = oggetto;
        this.rapportiIntervento = rapportiIntervento;
        this.richiesta = richiesta;
        this.stabile = stabile;
        this.stato = stato;
        this.priorità = priorità;
        this.foto = foto;
        this.nomeAmministratore = nomeAmministratore;
        this.nomeStabile = nomeStabile;
        this.indirizzoStabile = indirizzoStabile;
    }



    public String getIdTicketIntervento() {
        return idTicketIntervento;
    }

    public void setIdTicketIntervento(String idTicketIntervento) {
        this.idTicketIntervento = idTicketIntervento;
    }

    public String getUidAmministratore() {
        return uidAmministratore;
    }

    public void setUidAmministratore(String uidAmministratore) {
        this.uidAmministratore = uidAmministratore;
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

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public String getMessaggioCondomino() {
        return messaggioCondomino;
    }

    public void setMessaggioCondomino(String messaggioCondomino) {
        this.messaggioCondomino = messaggioCondomino;
    }

    public String getAggiornamentoCondomini() {
        return aggiornamentoCondomini;
    }

    public void setAggiornamentoCondomini(String aggiornamentoCondomini) {
        this.aggiornamentoCondomini = aggiornamentoCondomini;
    }

    public String getDescrizioneCondomini() {
        return descrizioneCondomini;
    }

    public void setDescrizioneCondomini(String descrizioneCondomini) {
        this.descrizioneCondomini = descrizioneCondomini;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getRapportiIntervento() {
        return rapportiIntervento;
    }

    public void setRapportiIntervento(String rapportiIntervento) {
        this.rapportiIntervento = rapportiIntervento;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNomeAmministratore() {
        return nomeAmministratore;
    }

    public void setNomeAmministratore(String nomeAmministratore) {
        this.nomeAmministratore = nomeAmministratore;
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
