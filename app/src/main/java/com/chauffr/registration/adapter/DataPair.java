package com.chauffr.registration.adapter;

/**
 * Created by Wenyue on 13/12/2015.
 */
public class DataPair {
    private String origin=null, trimmed=null;
    public DataPair(String origin, String trimmed){
        this.origin = origin;
        this.trimmed = trimmed;
    }

    public String getTrimmed(){
        return trimmed;
    }

    public String getOrigin(){
        return origin;
    }

    public boolean contain(String origin){
        return this.origin.equals(origin);
    }
}
