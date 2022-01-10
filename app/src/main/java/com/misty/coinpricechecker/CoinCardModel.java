package com.misty.coinpricechecker;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CoinCardModel {

    private String coinSymbol;
    private String coinName;
    private double coinConversionRate;
    private Timestamp timeOfConversion;

    public CoinCardModel(String coinSymbol, String coinName, double coinConversionRate, Timestamp timeOfConversion) {
        this.coinSymbol = coinSymbol;
        this.coinName = coinName;
        this.coinConversionRate = coinConversionRate;
        this.timeOfConversion = timeOfConversion;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public double getCoinConversionRate() {
        return coinConversionRate;
    }

    public void setCoinConversionRate(double coinConversionRate) {
        this.coinConversionRate = coinConversionRate;
    }

    public Timestamp getTimeOfConversion() {
        return timeOfConversion;
    }

    public void setTimeOfConversion(Timestamp timeOfConversion) {
        this.timeOfConversion = timeOfConversion;
    }
}
