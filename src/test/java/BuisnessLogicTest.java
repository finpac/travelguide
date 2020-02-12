/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BL.TravelGuideBL;
import DAL.LocationData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Patrick
 */
public class BuisnessLogicTest {
    
//    public BuisnessLogicTest() {
//    }
//    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    @Test
    public void testGetWeatherData() throws Exception {
        int selectedTown = 0;
        TravelGuideBL instance = TravelGuideBL.getTravelGuideData();
        String city = "Tiefernitz";
        LocationData t = new LocationData(city,"8324", "AT");
        instance.addLocation(t);
        assertEquals(city, instance.getLocdata().get(selectedTown).getCity());
        
    }
}
