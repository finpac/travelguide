/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAL.LocationData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String PATH = "weather";
    private static final String APPID = "4df9d24b9abf327077da7f062fa6e95e";
    private final static File FILE = new File("Location.xml");
    private Document docu;

    public void getWeather(String city, String zip, String country)
    {
        Client c = ClientBuilder.newClient();

        Response r = c.target(URI)
                .path(PATH)
                .queryParam("appid", APPID)
                .queryParam("zip", "8083,at")
                .request(MediaType.APPLICATION_JSON)
                .get();

//        HTTP Status Codes abfragen
//        r.getStatus();
        
        String jsonString = r.readEntity(String.class);
        
        System.out.println(jsonString);
    }

    public void addLocation(LocationData ld) {
        locdata.add(ld);
    }

    public ArrayList<LocationData> getLocdata() {
        return locdata;
    }
    
    public Document createDocument(String rootElement) {
        Document d = new Document() {};
        Element root = new Element(rootElement);
        d.setRootElement(root);
        return d;
    }

    /*
        This Methode deletes the old xml if exists and writes the updated xml with 
        the common hierarchy
        https://developers.google.com/apps-script/reference/xml-service/format
    */
    public void writeXML(Document doc) throws FileNotFoundException, IOException 
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
        }
            
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
        This Methode loads the saved XML file into a document
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
        This Methode gets the Data from the saved XML
         */
    private void getData() {       
        Element root = docu.getRootElement();
        List<Element> data = root.getChildren("Location");
        for (Element b : data) {
            System.out.println(data.size());
            String cities = null;
            String zip = null;
            String country = null;
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

    public void remove(int selectedIndex) {
        locdata.remove(selectedIndex);
    }
    
}
