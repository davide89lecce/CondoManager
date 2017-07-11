package com.gambino_serra.condomanager.Model.JsonSerializable;


import com.google.gson.annotations.SerializedName;

public class JsonCondominio {

    @SerializedName("idCondominio")
    public String idCondominio;
    @SerializedName("condominio")
    public String condominio;
    @SerializedName("indirizzo")
    public String indirizzo;
    @SerializedName("amministratore")
    public String ammministratore;

}
