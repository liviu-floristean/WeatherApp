package ro.mta.se.lab.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ro.mta.se.lab.Model.City;
import ro.mta.se.lab.ParserFile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ApplicationController implements Initializable {

    private City cityData;
    private ParserFile parser;
    private ArrayList<String> cityList;
    private ObservableList<String> countryList;

    @FXML
    private ComboBox<String> countryComboBox;

    public ComboBox<String> getCountryComboBox() {
        return countryComboBox;
    }

    public ComboBox<String> getCityComboBox() {
        return cityComboBox;
    }

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private Label cityLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label weatherLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windLabel;

    public ApplicationController() throws IOException {
        this.cityList = new ArrayList<String>();
        this.countryComboBox = new ComboBox<String>();
        this.parser = new ParserFile();
        this.cityLabel = new Label();
        this.dateLabel = new Label();
        this.weatherLabel = new Label();
        this.temperatureLabel = new Label();
        this.humidityLabel = new Label();
        this.windLabel = new Label();

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

        cityData = new City(city, sys.get("country").toString(), weather0.get("main").toString(), weather0.get("description").toString(), wind.get("speed").toString(),
                main.get("temp").toString(), main.get("humidity").toString());
    }

    private void fillData()
    {
        cityLabel.setText(cityData.getCityName());
        countryLabel.setText(cityData.getCountryName());

        String weatherData = cityData.getMainWeatherDescription() + ": " + cityData.getWeatherDescription();
        weatherLabel.setText(weatherData);

        double celsiusTemperature = Double.parseDouble(cityData.getTemperature());
        celsiusTemperature -= 273;

        long intTemperature = Math.round(celsiusTemperature);
        temperatureLabel.setText(String.valueOf(intTemperature) + " C");

        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText((date.format(now)).toString());

        humidityLabel.setText("Humidity: "+ cityData.getHumidity() + "%");
        windLabel.setText("Wind: "+ cityData.getWindSpeed() + "m/s");
    }

    private void writeSearchHistory() throws IOException {
        FileWriter file = new FileWriter(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\search_history.txt", true);

        file.write(dateLabel.getText() + ": " + cityComboBox.getValue() + " " + countryComboBox.getValue()
                + " - temperature registered is " + temperatureLabel.getText() +"\n");

        file.close();
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

        try {
            connection.setRequestMethod("POST");

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
        }catch (IOException e){
            e.printStackTrace();
        }

        jsonFile.close();
        connection.disconnect();

        parseJson();
        fillData();
        writeSearchHistory();
    }
}
