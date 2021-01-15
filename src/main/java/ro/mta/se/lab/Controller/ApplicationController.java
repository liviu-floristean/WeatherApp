package ro.mta.se.lab.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Paths;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import ro.mta.se.lab.Model.City;
import ro.mta.se.lab.ParserFile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ApplicationController implements Initializable {

    private ObservableList<City> cityData;
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

        countryList = parser.getCountryList();
        cityList = parser.getCities();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(countryList);
    }

    private void parseJson() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\json.txt"));
        JSONObject jo = (JSONObject) obj;

        Map sys = (Map)jo.get("sys");
        String city = (String)jo.get("name");

        JSONArray weather = (JSONArray) jo.get("weather");
        Map wind = (Map)jo.get("wind");
        Map main = (Map)jo.get("main");
        Map weather0 = (Map)weather.get(0);

        System.out.println(sys.get("country"));
        System.out.println(city);
        System.out.println(weather0.get("main"));
        System.out.println(weather0.get("description"));
        System.out.println(wind.get("speed"));
        System.out.println(main.get("temp"));
        System.out.println(main.get("humidity"));
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

    public void chooseCity(ActionEvent event) throws IOException, ParseException {
        String selectedCountry = countryComboBox.getValue();
        String selectedCity = cityComboBox.getValue();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+selectedCity+","+selectedCountry+"&APPID=583ce53a85b6d57fbe498249c2f341a8";

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        FileWriter jsonFile = new FileWriter(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\json.txt");

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        if(status>200)
        {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            while((line = reader.readLine()) != null)
            {
                jsonFile.write(line);
            }
            reader.close();
        }
        else
        {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line = reader.readLine()) != null)
            {
                jsonFile.write(line);
            }
            reader.close();
        }
        jsonFile.close();
        connection.disconnect();

        parseJson();
    }
}
