package com.gambino_serra.condomanager.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by condomanager on 13/02/17.
 */

public class JsonImpianto {

    @SerializedName("idImpianto")
    public Integer idImpianto;
    @SerializedName("impianto")
    public String impianto;
    @SerializedName("descrizione")
    public String descrizione;
    @SerializedName("note")
    public String note;
    @SerializedName("condominio")
    public String condominio;
    @SerializedName("fornitore")
    public String fornitore;

}
