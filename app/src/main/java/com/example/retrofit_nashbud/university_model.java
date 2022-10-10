package com.example.retrofit_nashbud;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class university_model {


    private String country, name, alpha_two_code;


    @SerializedName("state-province")
    private String state_province;

    public university_model() {
    }

    public university_model(String country, String name, String alpha_two_code, String state_province) {
        this.country = country;
        this.name = name;
        this.alpha_two_code = alpha_two_code;
        this.state_province = state_province;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha_two_code() {
        return alpha_two_code;
    }

    public void setAlpha_two_code(String alpha_two_code) {
        this.alpha_two_code = alpha_two_code;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }
}
