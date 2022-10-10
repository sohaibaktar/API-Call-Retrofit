package com.example.retrofit_nashbud;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class university_model {


    String country, name, alpha_two_code;
 private List<String> domains;
 private List<String> web_pages;


    @SerializedName("state-province")
    String state_province;
//    ArrayList<web_pages> web_pages;
//    ArrayList<domains> domains;


    public university_model() {
    }

    public university_model(String country, String name, String alpha_two_code, List<String> domains, List<String> web_pages, String state_province) {
        this.country = country;
        this.name = name;
        this.alpha_two_code = alpha_two_code;
        this.domains = domains;
        this.web_pages = web_pages;
        this.state_province = state_province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getWeb_pages() {
        return web_pages;
    }

    public void setWeb_pages(List<String> web_pages) {
        this.web_pages = web_pages;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
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
