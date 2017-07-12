package com.gambino_serra.condomanager_amministratore.Model.JsonSerializable;

import com.google.gson.annotations.SerializedName;

public class JsonAmministratore {

    @SerializedName("usernameA")
    public String username;
    @SerializedName("studio")
    public String studio;
    @SerializedName("amministratore")
    public String amministratore;
    @SerializedName("sede")
    public String sede;
    @SerializedName("telefono")
    public String telefono;
    @SerializedName("email")
    public String email;
}
