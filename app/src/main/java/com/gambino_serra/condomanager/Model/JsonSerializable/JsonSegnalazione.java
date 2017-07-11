package com.gambino_serra.condomanager.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

public class JsonSegnalazione {

    @SerializedName("idSegnalazione")
    public Integer idSegnalazione;
    @SerializedName("data")
    public String data;
    @SerializedName("segnalazione")
    public String segnalazione;
    @SerializedName("condomino")
    public String condomino;
    @SerializedName("idCondominio")
    public Integer idCondominio;
    @SerializedName("impianto")
    public Integer impianto;
    @SerializedName("fornitore")
    public String fornitore;
    @SerializedName("stato")
    public String stato;
    @SerializedName("condominio")
    public String condominio;

}
