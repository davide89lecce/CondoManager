package com.gambino_serra.condomanager.View.Amministratore.NuovoTicket;

public class DataCondominio {

    String idCondominio;
    String condominio;
    String indirizzo;
    String amministratore;

    public DataCondominio(String idCondominio, String condominio, String indirizzo, String amministratore) {
        this.idCondominio = idCondominio;
        this.condominio = condominio;
        this.indirizzo = indirizzo;
        this.amministratore = amministratore;
    }


    public String getCondominio() {
        return condominio;
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public String getAmministratore() {
        return amministratore;
    }

    public String getIdCondominio() {
        return idCondominio;
    }
}