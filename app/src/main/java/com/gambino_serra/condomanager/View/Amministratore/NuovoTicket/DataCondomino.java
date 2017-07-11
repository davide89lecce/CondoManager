package com.gambino_serra.condomanager.View.Amministratore.NuovoTicket;

public class DataCondomino {

    String usernameC;
    String condomino;

    public DataCondomino(String usernameC, String condomino) {
        this.usernameC = usernameC;
        this.condomino = condomino;
    }

    public String getUsernameC() {
        return usernameC;
    }

    public String getCondomino() {
        return condomino;
    }
}