package com.zillion.crypto_price_details;

/**
 * Created by haide on 2/13/2018.
 */

public class cryptoCurrency {

    private String symbol = "";
    private String cap = "";
    private String hour = "";
    private String day = "";
    private String price = "";
    private String heat = "";

    public String getSymbol(){
        return symbol;
    }
    public String getCap(){
        return cap;
    }
    public String getHour(){
        return hour;
    }
    public String getDay(){
        return day;
    }
    public String getPrice(){
        return price;
    }
    public String getHeat(){
        return heat;
    }


    public void setSymbol(String s){
        symbol = s;
    }
    public void setCap(String s){
        cap = s;
    }
    public void setHour(String s){
        hour = s;
    }
    public void setDay(String s){
        day = s;
    }
    public void setPrice(String s){
        price = s;
    }
    public void setHeat(String s){
        heat = s;
    }
}
