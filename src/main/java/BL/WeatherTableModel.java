
package BL;


import DAL.WeatherComperation;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Patrick
 */
public class WeatherTableModel extends AbstractTableModel {
    
    private ArrayList<WeatherComperation> weather = new ArrayList<>();
    
    /**
    Sort Algorythms by temp
     */
    private Comparator<WeatherComperation> compareByTemp = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            double d1 = Double.parseDouble(String.valueOf(o1.getTemp()).substring(0, 4));
            double d2 = Double.parseDouble(String.valueOf(o2.getTemp()).substring(0, 4));
            return d1 > d2 ? -1 : (d1 < d2) ? 1 : 0;
        }
    };
    /**
    Sort Algorythms by humidity
     */
    private Comparator<WeatherComperation> compareByHumidity = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getHumidity() > o2.getHumidity() ? -1 : (o1.getHumidity() < o2.getHumidity()) ? 1 : 0;
        }
    };
    /**
    Sort Algorythms by preasure
     */
    private Comparator<WeatherComperation> compareByPreasure = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getAirPressure()> o2.getAirPressure()? -1 : (o1.getAirPressure()< o2.getAirPressure()) ? 1 : 0;
        }
    };

    /**
    adds the weahterdata 
     */
    public void addWeatherData(WeatherComperation w) {
        weather.add(w);
    }
    /**
    gets weatherdata
     */
    public WeatherComperation getWeatherData(int index)
    {
        return weather.get(index);
    }
    /**
    clears weatherList
     */
    public void clearPurposeList() {
        weather.clear();
    }

    @Override
    public int getRowCount() {
        return weather.size();
    }
    private String[] wp = {
        "Ort", "Temperatur", "Feuchtigkeit", "Luftdruck"
    };

    @Override
    public String getColumnName(int column) {
        return wp[column];
    }

    @Override
    public int getColumnCount() {
        return wp.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return weather.get(rowIndex).getCity();
            case 1:
                return String.valueOf(weather.get(rowIndex).getTemp()).substring(0, 4) +" C°";
            case 2:
                return weather.get(rowIndex).getHumidity() + " %";
            case 3:
                return weather.get(rowIndex).getAirPressure()+ " hPa";
            default:
                return "error";

        }
    }

    /**
    Which sort methode was choosen 
     */
    public void sortList(int sortMech) {
        if (sortMech == 1) {
            weather.sort(compareByTemp);
        }
        if (sortMech == 2) {
            weather.sort(compareByHumidity);
        }
        if (sortMech == 3) {
            weather.sort(compareByPreasure);
        }

    }
}
