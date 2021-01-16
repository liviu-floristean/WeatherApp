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

/**
 * Aceasta clasa contine functionalitatea de baza a aplicatiei,
 * intrucat comunica cu interfata grafica si proceseaza datele.
 * @author Floriștean Liviu
 */

public class ApplicationController implements Initializable {

    /**
     * Descrierea membrilor clasei:
     *
     * <b>cityData</b> este un obiect de tip City si contine toate
     * informatiile despre vremea intr-un oras
     *
     * <b>parser</b> este un obiect de tip parser si are rolul de
     * a parsa fisierul de intrare
     *
     * <b>cityList</b> este un obiect de tip ArrayList si contine
     * toate orasele gasite in fisierul de intrare
     *
     * <b>countryList</b> este un obiect de tip ObservableList
     * si contine toate tarile gasite in fisierul de intrare, fara
     * duplicate
     *
     * <p>Restul elementelor reprezinta componentele care alcatuiesc
     * interfata grafica. Cele doua combobox-uri au rolul de a oferi
     * utilizatorului posibilitatea de a alege tara si orasul pentru
     * care doreste sa cunoasca vremea. Restul componentelor sunt de
     * tip Label si au rol de afisare a datelor procesare de aceasta
     * clasa.</p>
     */

    private City cityData;
    private ParserFile parser;
    private ArrayList<String> cityList;
    private ObservableList<String> countryList;

    @FXML
    private ComboBox<String> countryComboBox;

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

    /**
     * Constructorul clasei, in acesta se initializeaza toti membrii
     * si se parseaza fisierul de intrare. Cu datele obtinute din el
     * se vor popula membrii countryList si cityList.
     * @throws IOException
     */

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

    /**
     * Metoda de initializare a combobox-ului pentru tari.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(countryList);
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }

    public ObservableList<String> getCountryList() {
        return countryList;
    }

    /**
     * Metoda pentru parsarea fisierului JSON, care contine datele
     * primite de la API. S-a folosit json-simple pentru obtinerea
     * datelor care ne intereseaza in cadrul rularii aplicatiei. Cu
     * datele obtinute se va construi obiectul de tip cityData.
     * @throws IOException
     * @throws ParseException
     */

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

    /**
     * Metoda care are rolul de a afisa in interfata grafica toate
     * datele referitoare la vremea in orasul selectat de catre
     * utilizator.
     */

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

    /**
     * Metoda care are rolul de a scrie intr-un fisier de iesire tot
     * istoricul cautarilor aplicatiei.
     * @throws IOException
     */

    private void writeSearchHistory() throws IOException {
        FileWriter file = new FileWriter(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\search_history.txt", true);

        file.write(dateLabel.getText() + ": " + cityComboBox.getValue() + " " + countryComboBox.getValue()
                + " - temperature registered is " + temperatureLabel.getText() +"\n");

        file.close();
    }

    /**
     * Metoda de functionalitate a combobox-ului countryComboBox.
     * Acesta este populat cu datele obtinute din parser si este
     * necesar pentru alegerea ulterioara a unui oras.
     * @param event
     */

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

    /**
     * Metoda de functionalitate a combobox-ului cityComboBox.
     * Este necesara alegerea unei tari din celalalt combobox,
     * deoarece in functie de tara aleasa se vor afisa orasele
     * valide. In urma alegerii unui oras, aplicatia va face o
     * cerere catre API pentru a primi datele corespunzatoare
     * orasului ales. Datele primite sunt salvate ulterior in
     * fisierul json.txt. Acesta va fi parsat, iar datele din
     * acesta vor fi afisate in interfata grafica.
     * @param event
     * @throws IOException
     * @throws ParseException
     */

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
