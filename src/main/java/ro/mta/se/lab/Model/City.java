package ro.mta.se.lab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clasa care contine toate datele despre un oras
 * ales de catre utilizator.
 *
 * @author Floriștean Liviu
 */

public class City{

    /**
     * Descrierea membrilor
     *
     * Toti membrii sunt de tip StringProperty si
     * reprezinta datele de baza pe care aplicatia
     * le va afisa in urma alegerii facute de catre
     * utilizator.
     */

    StringProperty cityName;
    StringProperty countryName;
    StringProperty mainWeatherDescription;
    StringProperty weatherDescription;
    StringProperty windSpeed;
    StringProperty temperature;
    StringProperty humidity;

    /**
     * Constructorul clasei City
     * Acesta primeste ca parametrii cate un string pentru
     * fiecare membru care intra in componenta clasei.
     * @param city_name
     * @param country_name
     * @param main_weather
     * @param weather
     * @param wind
     * @param temper
     * @param humid
     */

    public City(String city_name, String country_name, String main_weather, String weather, String wind, String temper, String humid)
    {
        cityName = new SimpleStringProperty(city_name);
        countryName = new SimpleStringProperty(country_name);
        mainWeatherDescription = new SimpleStringProperty(main_weather);
        weatherDescription = new SimpleStringProperty(weather);
        windSpeed = new SimpleStringProperty(wind);
        temperature = new SimpleStringProperty(temper);
        humidity = new SimpleStringProperty(humid);
    }

    /**
     * Urmatoarele metode reprezinta getters si setters pentru
     * fiecare membru din clasa.
     *
     */

    public String getCityName() {
        return cityName.get();
    }

    public StringProperty cityNameProperty() {
        return cityName;
    }

    public String getCountryName() {
        return countryName.get();
    }

    public StringProperty countryNameProperty() {
        return countryName;
    }

    public String getMainWeatherDescription() {
        return mainWeatherDescription.get();
    }

    public StringProperty mainWeatherDescriptionProperty() {
        return mainWeatherDescription;
    }

    public String getWeatherDescription() {
        return weatherDescription.get();
    }

    public StringProperty weatherDescriptionProperty() {
        return weatherDescription;
    }

    public String getWindSpeed() {
        return windSpeed.get();
    }

    public StringProperty windSpeedProperty() {
        return windSpeed;
    }

    public String getTemperature() {
        return temperature.get();
    }

    public StringProperty temperatureProperty() {
        return temperature;
    }

    public String getHumidity() {
        return humidity.get();
    }

    public StringProperty humidityProperty() {
        return humidity;
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

    public void setMainWeatherDescription(String mainWeatherDescription) {
        this.mainWeatherDescription.set(mainWeatherDescription);
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription.set(weatherDescription);
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed.set(windSpeed);
    }

    public void setTemperature(String temperature) {
        this.temperature.set(temperature);
    }

    public void setHumidity(String humidity) {
        this.humidity.set(humidity);
    }
}
