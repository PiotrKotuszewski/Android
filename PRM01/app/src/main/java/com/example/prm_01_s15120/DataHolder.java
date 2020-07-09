package com.example.prm_01_s15120;

import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    final Map<String, Double> borrowers = new HashMap<>();
    private static DataHolder instance;

    private DataHolder(){}

    static DataHolder getInstance(){
        if( instance == null){
            instance = new DataHolder();
        }
        return instance;
    }
}
