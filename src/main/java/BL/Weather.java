/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

/**
 *
 * @author Patrick
 */
public class Weather {
    private int weatherID;
    private int maxTemp;
    private int minTemp;
    private int currentTemp;
    private int windspeed;
    private int winddegree;
    private int pressure;
    private int humidity;
    private String clouds;
    private String datetime;
    

    public Weather(int weatherID, int maxTemp, int minTemp, int currentTemp, int windspeed, int winddegree, int pressure, int humidity, String clouds, String datetime) {
        this.weatherID = weatherID;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.currentTemp = currentTemp;
        this.windspeed = windspeed;
        this.winddegree = winddegree;
        this.pressure = pressure;
        this.humidity = humidity;
        this.clouds = clouds;
        this.datetime = datetime;
        
    }

    public int getWeatherID() {
        return weatherID;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getWindspeed() {
        return windspeed;
    }

    public int getWinddegree() {
        return winddegree;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getClouds() {
        return clouds;
    }

    public String getDatetime() {
        return datetime;
    }    
}
