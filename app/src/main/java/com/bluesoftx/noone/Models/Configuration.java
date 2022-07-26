package com.bluesoftx.noone.Models;

public class Configuration {

    private String testPrivate;
    public String testPublic;

    public Configuration(String testPrivate, String testPublic){

        this.testPrivate = testPrivate;
    }

    public String getText() {
        return testPublic;
    }
    public String getTestPrivate(){return testPrivate;}

    public String test (){
        return getTestPrivate();
    }
}
