
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
    
    private ArrayList<WeatherComperation> wcomp = new ArrayList<>();    
   
    /**
    Add the data for the comperationList
     */
    public void addWeatherData(WeatherComperation w) {
        wcomp.add(w);
    }
    /**
    Get the data from a selected index of the comperationList
     */
    public WeatherComperation getWeatherData(int index)
    {
        return wcomp.get(index);
    }
    /**
    clears the data of the comperationList
     */
    public void clearPurposeList() {
        wcomp.clear();
    }

    /**TableModel methods
     * 
     * @return 
     */
    @Override
    public int getRowCount() {
        return wcomp.size();
    }
    private String[] columntext = {
        "City", "Temperature", "Humidity", "Preasure"
    };

    @Override
    public String getColumnName(int column) {
        return columntext[column];
    }

    @Override
    public int getColumnCount() {
        return columntext.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return wcomp.get(rowIndex).getCity();
            case 1:
                return String.valueOf(wcomp.get(rowIndex).getTemp()).substring(0, 4) +" °C";
            case 2:
                return wcomp.get(rowIndex).getHumidity() + " %";
            case 3:
                return wcomp.get(rowIndex).getAirPressure()+ " hPa";
            default:
                return "no data";
        }
    }

    /**
    Check which selection should be run
     */
    public void sortList(int sortSelection) {
        if (sortSelection == 1) {
            wcomp.sort(compareByTemp);
        }
        if (sortSelection == 2) {
            wcomp.sort(compareByHumidity);
        }
        if (sortSelection == 3) {
            wcomp.sort(compareByPreasure);
        }
    }
    
     /**
     Compares the temps
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
    Compares the humidities
     */
    private Comparator<WeatherComperation> compareByHumidity = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getHumidity() > o2.getHumidity() ? -1 : (o1.getHumidity() > o2.getHumidity()) ? 1 : 0;
        }
    };
    /**
    Compares the preasures
     */
    private Comparator<WeatherComperation> compareByPreasure = new Comparator<WeatherComperation>() {
        @Override
        public int compare(WeatherComperation o1, WeatherComperation o2) {
            return o1.getAirPressure()> o2.getAirPressure()? -1 : (o1.getAirPressure()< o2.getAirPressure()) ? 1 : 0;
        }
    };
}
