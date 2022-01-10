package com.misty.coinpricechecker;

public class CoinCardModel {

    private String coinSymbol;
    private String coinName;
    private double coinConversionRate;
    private String timeOfConversion;

    public CoinCardModel(String coinSymbol, String coinName, double coinConversionRate, String timeOfConversion) {
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

    public String getTimeOfConversion() {
        return timeOfConversion;
    }

    public void setTimeOfConversion(String timeOfConversion) {
        this.timeOfConversion = timeOfConversion;
    }
}
