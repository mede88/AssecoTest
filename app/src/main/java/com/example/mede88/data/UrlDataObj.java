package com.example.mede88.data;

import android.content.Context;
import android.content.SharedPreferences;




public class UrlDataObj {

    public enum LocationType {DATABASE, SHAREDPREFERENCES}

    private String url;
    private String hash;
    private LocationType locationType;


    public UrlDataObj(String url, String hash, LocationType locationType) {
        this.url = url;
        this.hash = hash;
        this.locationType = locationType;

    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
}
