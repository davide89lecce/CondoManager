package com.gambino_serra.condomanager_amministratore.View.Amministratore.TicketRifiutato;

public class DataTicketCategoria {

    String name;
    String descrizione;

    public DataTicketCategoria(String name, String descrizione) {
        this.name = name;
        this.descrizione = descrizione;
    }

    public String getName() {
        return name;
    }

    public String getDescrizione() {
        return descrizione;
    }
}