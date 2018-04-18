package com.example.u.ussc;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ethan on 4/17/18.
 */

public class ReqObject {
    @SerializedName("pre-req")
    public String prereq;
    @SerializedName("co-req")
    public String coreq;
}
