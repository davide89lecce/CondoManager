package com.gambino_serra.condomanager.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by condomanager on 13/02/17.
 */

public class JsonCondomino {

    @SerializedName("usernameC")
    public String username;
    @SerializedName("nome")
    public String nome;
    @SerializedName("cognome")
    public String cognome;
    @SerializedName("condominio")
    public String condominio;
}
