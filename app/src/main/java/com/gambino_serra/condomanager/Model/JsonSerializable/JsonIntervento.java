package com.gambino_serra.condomanager.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by condomanager on 13/02/17.
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
