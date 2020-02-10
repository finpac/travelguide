/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BL.TravelGuideBL;
import BL.WeatherTableModel;
import DAL.WeatherComperation;

/**
 *
 * @author Patrick
 */
public class CompToolGUI extends javax.swing.JDialog {

    /**
     * Creates new form CompToolDialog
     */
    public int travelday = 0;
    private CompareToolDialog dot ;
    private WeatherTableModel tb;
    public TravelGuideBL  tgbl = TravelGuideBL.getTravelGuideData();
    
    public CompToolGUI(java.awt.Frame parent, boolean modal, CompareToolDialog dot) {
        super(parent, modal);
        this.tb = new WeatherTableModel();
       
        initComponents();
        this.dot = dot;
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbWeather = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(452, 250));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setViewportView(tbWeather);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**Initialize the Tablemodel and checks which day and sort was selected in the Dialog. Gets the weather data from selected day
     * 
     */
    private void init() {
        TravelGuideBL bl = tgbl.getWeatherData(0);
        this.setTitle(dot.getTravelday());
        String day3 = bl.getList().get(bl.getNextDay()+8).getDt_txt().replace('-', '.').substring(0, 10);
        String day4 = bl.getList().get(bl.getNextDay()+16).getDt_txt().replace('-', '.').substring(0, 10);
        String day5 = bl.getList().get(bl.getNextDay()+24).getDt_txt().replace('-', '.').substring(0, 10);
        if("TODAY".equals(dot.getTravelday())) {
            travelday = 0;
        }else if("TOMORROW".equals(dot.getTravelday())){
            travelday = bl.getNextDay();
        }else if (dot.getTravelday().equals(day3)){
            travelday = bl.getNextDay()+8;
        }else if(dot.getTravelday().equals(day4)){
            travelday = bl.getNextDay()+16;
        }else if(dot.getTravelday().equals(day5)){
            travelday = bl.getNextDay()+24;
        }else{           
            travelday = 0;
        }
        tb.clearPurposeList();
        for (int i = 0; i < tgbl.getLocdata().size(); i++) {
            TravelGuideBL trav;
                trav = tgbl.getWeatherData(i);
                tb.addWeatherData(new WeatherComperation(tgbl.getLocdata().get(i).getCity(), 
                        (float) (trav.avgTemp(travelday)),
                        trav.getList().get(travelday).getMain().getHumidity(),
                        trav.getList().get(travelday).getMain().getPressure()));            
        }
        tb.sortList(dot.returnSort());
        System.out.println(dot.returnSort());
        tbWeather.setModel(tb);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbWeather;
    // End of variables declaration//GEN-END:variables
}
