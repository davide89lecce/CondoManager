package com.gambino_serra.condomanager_amministratore.View.Amministratore.NuovoTicket;

public class DataFornitore {

    String fornitore;
    String indirizzo;
    String usernameF;

    public DataFornitore(String fornitore, String indirizzo, String usernameF) {
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