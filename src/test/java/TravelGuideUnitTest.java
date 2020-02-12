/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BL.WeatherTableModel;
import DAL.WeatherComperation;
import java.util.ArrayList;
import java.util.Comparator;
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
public class TravelGuideUnitTest {
    
//    public TravelGuideUnitTest() {
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

    private Comparator<WeatherComperation> compareByHumidity = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getHumidity() > o2.getHumidity() ? -1 : (o1.getHumidity() < o2.getHumidity()) ? 1 : 0;
        }
    };
    private Comparator<WeatherComperation> compareByPreasure = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getAirPressure()> o2.getAirPressure()? -1 : (o1.getAirPressure()< o2.getAirPressure()) ? 1 : 0;
        }
    };
    
    @Test
    public void testSortList() {
        WeatherTableModel instance = new WeatherTableModel();
        instance.addWeatherData(new WeatherComperation("St. Stefan im Rosental", 4, 2, 1));
        instance.addWeatherData(new WeatherComperation("Pirching", 5, 1, 2));
        instance.addWeatherData(new WeatherComperation("Hurghada", 0, 6, 10));
        instance.addWeatherData(new WeatherComperation("Gnas", 1, 3, 8));
        ArrayList<WeatherComperation> wp = new ArrayList();
        wp.add(new WeatherComperation("St. Stefan im Rosental", 4, 2, 1));
        wp.add(new WeatherComperation("Pirching", 5, 1, 2));
        wp.add(new WeatherComperation("Hurghada", 0, 6, 10));
        wp.add(new WeatherComperation("Gnas", 1, 3, 8));
        instance.sortList(2);
        wp.sort(compareByHumidity);
        assertEquals(wp.get(0).getHumidity()+ " %",instance.getValueAt(0, 2));
        instance.sortList(3);
        wp.sort(compareByPreasure);
        assertEquals(wp.get(0).getAirPressure()+ " hPa",instance.getValueAt(0, 3));
        
    }
}
