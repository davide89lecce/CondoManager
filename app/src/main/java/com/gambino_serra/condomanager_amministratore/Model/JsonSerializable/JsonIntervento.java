package com.gambino_serra.condomanager_amministratore.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by condomanager_amministratore on 13/02/17.
 */

public class JsonIntervento {

    @SerializedName("idIntervento")
    public Integer idIntervento;
    @SerializedName("data")
    public String data;
    @SerializedName("intervento")
    public String intervento;
    @SerializedName("segnalazione")
    public String segnalazione;
}
