package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento;

public class DataCategoriaIntevento {

    String name;
    String descrizione;

    public DataCategoriaIntevento(String name, String descrizione) {
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