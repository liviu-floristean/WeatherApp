package ro.mta.se.lab.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import ro.mta.se.lab.ParserFile;

public class ApplicationController implements Initializable {

    private ParserFile parser;
    private ArrayList<String> cityList;
    ObservableList<String> countryList;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> cityComboBox;

    public ApplicationController() throws IOException {
        this.cityList = new ArrayList<String>();
        this.countryComboBox = new ComboBox<String>();
        this.parser = new ParserFile();
        parser.parse();

    //    countryList = FXCollections.observableArrayList("Germany", "Romania", "Russia");

        countryList = parser.getCountryList();
        cityList = parser.getCities();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(countryList);
    }

    public void chooseCountry(ActionEvent event)
    {
        ObservableList<String> validCities = FXCollections.observableArrayList();
        String countrySelected = countryComboBox.getValue();

        for(int i = 0; i<cityList.size(); i+=2)
        {
            if(cityList.get(i).equals(countrySelected))
            {
                validCities.add(cityList.get(i+1));
            }
        }

        cityComboBox.setItems(validCities);
    }

    public void chooseCity(ActionEvent event) throws IOException {
        String selectedCountry = countryComboBox.getValue();
        String selectedCity = cityComboBox.getValue();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+selectedCity+","+selectedCountry+"&APPID=583ce53a85b6d57fbe498249c2f341a8";

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
    //    System.out.println(status);

        if(status>200)
        {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            while((line = reader.readLine()) != null)
            {
                responseContent.append(line);
            }
            reader.close();
        }
        else
        {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line = reader.readLine()) != null)
            {
                responseContent.append(line);
            }
            reader.close();
        }
        System.out.println(responseContent.toString());


    //    InputStream instream = connection.getInputStream();
     //   BufferedReader reader = new BufferedReader(new InputStreamReader(instream));

     //   while(reader.readLine() != null)
     //   {
     //       json_str = reader.readLine();
     //       System.out.println(json_str);
     //   }

    //    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    //    System.out.println(in);
    }
}
