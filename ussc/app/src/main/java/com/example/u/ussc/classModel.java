package com.example.u.ussc;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ethan on 4/4/18.
 */

public class classModel {

    @SerializedName("database")
    public ArrayList<classObject> list;

    static public class classObject {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("description")
        public String description;
        @SerializedName("credits")
        public String credits;
//        @SerializedName("prerequisites")
//        public String prerequisites;

    }
}
