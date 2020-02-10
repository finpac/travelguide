/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAL.LocationData;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import DAL.OpenWeatherResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Patrick
 */
public class TravelGuideBL {
    
    public ArrayList <LocationData> locdata = new ArrayList <> ();
    private static final String URI = "http://api.openweathermap.org/data/2.5/";
    private static final String PATH = "forecast";
    private static final String APPID = "a291907fdfb9543a4227821d089b5bf0";
    private final static File FILE = new File("Location.xml");
    private Document docu;
    public TravelGuideBL respo;   
    public List <OpenWeatherResponse> list;   
    private boolean zipnotfound =false;
    private static TravelGuideBL tgb;
    
    /**Singleton Implementation because of some errors in CompareTool GUI and Dia
     * 
     * @return 
     */
    public static TravelGuideBL getTravelGuideData()
    {
        if(tgb == null)
        {
            tgb = new TravelGuideBL();
        }
        return tgb;
    }
    
    /**Returns OpenWeatherResponse List
     * 
     * @return 
     */
    public List<OpenWeatherResponse> getList() {
        return list;
    }

    /**This Method gets the Weather Data from Openweathermap and checks if there is a zip with country code or a city which can be found
     * 
     * @param i
     * @return 
     */
    public TravelGuideBL getWeatherData(int i)
    {
        Client c = ClientBuilder.newClient();
        
        Response resp ;    
    
        if(!locdata.get(i).getZip().isEmpty() && !zipnotfound)
        {           
            resp = c.target(URI).path(PATH).queryParam("appid", APPID)
                  .queryParam("zip",locdata.get(i).getZip() + "," + 
                          locdata.get(i).getCountry())
                  .request(MediaType.APPLICATION_JSON)
                  .get();

          String jsonStrg = resp.readEntity(String.class);
         // System.out.println(jsonStrg);
          respo = new Gson().fromJson(jsonStrg, TravelGuideBL.class);
          if(resp.getStatus() == 404)
          {
              //JOptionPane.showMessageDialog(null , "ZIP not found", "The entered zip could not be found", JOptionPane.ERROR_MESSAGE);
              zipnotfound = true;
              getWeatherData(i);
          }
        }
        else if(locdata.get(i).getZip().isEmpty() || zipnotfound)
        {
            resp = c.target(URI).path(PATH)
                    .queryParam("q", locdata.get(i).getCity())
                    .queryParam("appid", APPID)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            String jsonStrg = resp.readEntity(String.class);
            respo = new Gson().fromJson(jsonStrg, TravelGuideBL.class);
            zipnotfound = false;
            if(resp.getStatus() == 404)
            {
                JOptionPane.showMessageDialog(null , "City and ZIP not found", "The entered City and ZIP could not be found or the Country is invalid", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        return respo;  
    }

    /**Adds a location to Location ArrayList
     * 
     * @param ld 
     */
    public void addLocation(LocationData ld) {
        locdata.add(ld);
    }
    
    /**Removes a location
     * 
     * @param selectedIndex 
     */
    public void remove(int selectedIndex) {
        locdata.remove(selectedIndex);
    }

    /**returns the list for other classes
     * 
     * @return 
     */
    public ArrayList<LocationData> getLocdata() {
        return locdata;
    }
    
    /**Creates the document for saving in XML
     * 
     * @param rootElement
     * @return 
     */
    public Document createDocument(String rootElement) {
        Document d = new Document() {};
        Element root = new Element(rootElement);
        d.setRootElement(root);
        return d;
    }

    /**Prepares the xml Document, set the structure of it and the data from Locationlist
     * 
     * @param doc 
     */
    public void prepareXML(Document doc)
    {
        Element e1 = new Element("Location");
        Element e2 = new Element("City");
        Element e3 = new Element("Zip");
        Element e4 = new Element("Country");
        for (LocationData d : locdata) {
            e2.setText(d.getCity());
            e3.setText(d.getZip());
            e4.setText(d.getCountry());
            e1.addContent(e2);
            e1.addContent(e3);
            e1.addContent(e4);
            doc.getRootElement().addContent(e1);
            e1 = new Element("Location");
            e2 = new Element("City");
            e3 = new Element("Zip");
            e4 = new Element("Country");
        } 
    }
    /**
        This Methode removes the previous xml if it exists and save the updated xml with 
        the in preparexml created hierarchy
        https://developers.google.com/apps-script/reference/xml-service/format
        */
    public void writeXML(Document doc) throws FileNotFoundException, IOException 
    {                    
        Format format = Format.getCompactFormat();
        format.setIndent("    ");
        try {
            boolean isCreated = FILE.createNewFile();
            if (!isCreated) {
                throw new Exception();
            }
            FILE.delete();
        } catch (Exception ex) {
            FILE.delete();
        }
        FileOutputStream fos = new FileOutputStream(FILE);
        XMLOutputter xmlOut = new XMLOutputter(format);
        xmlOut.output(doc, fos);        
    }
    

 /**
  * This method loads the saved XML file into a document
  */
     
    public void loadData()   {       
        SAXBuilder sbuilder = new SAXBuilder();
        try {
            docu = sbuilder.build(FILE);
        } catch (JDOMException ex) {
            Logger.getLogger(TravelGuideBL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelGuideBL.class.getName()).log(Level.SEVERE, null, ex);
        }
        getData();
    }
    /**
        This Method gets the Data from the saved XML and put it into the list
    */
    public void getData() {       
        Element root = docu.getRootElement();
        List<Element> data = root.getChildren("Location");
        for (Element b : data) {
            String cities = "";
            String zip = "";
            String country = "";
            List<Element> locList = b.getChildren("City");
            for (Element city : locList) {
                cities = city.getText();
                break;
            }
            List<Element> zipList = b.getChildren("Zip");
            for (Element zips : zipList) {
                zip = zips.getText();
                break;
            }
            List<Element> countryList = b.getChildren("Country");
            for (Element countries : countryList) {
                country = countries.getText();
                break;
            }
            locdata.add(new LocationData(cities, zip, country));
        }
    }
   
    /**This method returns the next day because the forecast return weather data every 3hours
     * 
     * @return 
     */
    public int getNextDay()
    {      
        int forecastNum = 0;
        for(int i = 0; i < 9; i++)
            {             
                if(!getList().get(0).getDt_txt().substring(0, 10).equals(getList().get(i).getDt_txt().substring(0, 10)))
                {
                    forecastNum = i;                   
                    System.out.println(forecastNum);
                    break;
                }
            }
        return forecastNum;
    }
    
    /**This methode calculate the average max temp of a day
     * 
     * @param threehourzone
     * @return 
     */
    public float avgmaxTemp(int threehourzone)
    {
        float tempDayMax = (float) ((getList().get(threehourzone).getMain().getTemp_max() 
                + getList().get(threehourzone+3).getMain().getTemp_max()
                + getList().get(threehourzone+7).getMain().getTemp_max())/3
                - 273.15); 
        return tempDayMax;
    }
    
    /**This methode calculate the average min temp of a day
     * 
     * @param threehourzone
     * @return 
     */
    public float avgminTemp(int threehourzone)
    {
        float tempDayMin = (float) ((getList().get(threehourzone+3).getMain().getTemp_min()
                +getList().get(threehourzone+3).getMain().getTemp_min()
                +getList().get(threehourzone+7).getMain().getTemp_min())/3
                -273.15);
        return tempDayMin;
    }
    
    /**This methode calculate the average temp of a hole day
     * 
     * @param threehourzone
     * @return 
     */
    public float avgTemp(int threehourzone)
    {
        float tempDay = (float) ((getList().get(threehourzone).getMain().getTemp()
                +getList().get(threehourzone+3).getMain().getTemp()
                +getList().get(threehourzone+7).getMain().getTemp())/3 -273.15);
        return tempDay;
    }
    
    /**This methode calculate the average humidity of a hole day
     * 
     * @param threehourzone
     * @return 
     */
    public float avgHumidity(int threehourzone)
    {
        float humidDay = (float) ((getList().get(threehourzone).getMain().getHumidity()
                +getList().get(threehourzone+3).getMain().getHumidity()
                +getList().get(threehourzone+7).getMain().getHumidity())/3);
        return humidDay;
    }
    
    /**This methode calculate the average preasure of a hole day
     * 
     * @param threehourzone
     * @return 
     */
    public float avgPressure(int threehourzone)
    {
        float pressDay = (float) ((getList().get(threehourzone).getMain().getPressure()
                +getList().get(threehourzone+3).getMain().getPressure()
                +getList().get(threehourzone+7).getMain().getPressure())/3);
        
        return pressDay;
    }
}
