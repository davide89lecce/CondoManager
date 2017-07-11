package com.gambino_serra.condomanager.View.Amministratore.Segnalazione;

public class DataCategoria {

    String name;
    String descrizione;

    public DataCategoria(String name, String descrizione) {
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