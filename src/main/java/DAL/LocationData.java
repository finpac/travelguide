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
public class LocationData {
    public String city;
    public String zip;
    public String country;

    public LocationData(String city, String zip, String country) {
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }
    
    
    
}
