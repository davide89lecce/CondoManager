package com.gambino_serra.condomanager.View.Amministratore.TicketRifiutato;

public class DataTicketFornitore {

    String fornitore;
    String indirizzo;
    String usernameF;

    public DataTicketFornitore(String fornitore, String indirizzo, String usernameF) {
        this.fornitore = fornitore;
        this.indirizzo = indirizzo;
        this.usernameF = usernameF;
    }


    public String getFornitore() {
        return fornitore;
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public String getUsernameF() {
        return usernameF;
    }
}