/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

/**
 *
 * @author Patrick
 */
public class WeatherComperation {
    private String city;
    private float temp;
    private int humidity;
    private int airPressure;

    public WeatherComperation(String city, float temp, int humidity, int airPressure) {
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.airPressure = airPressure;
    }

    public String getCity() {
        return city;
    }

    public float getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getAirPressure() {
        return airPressure;
    }
    
    
}
