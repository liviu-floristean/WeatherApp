package ro.mta.se.lab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class ParserFile {
    private File configFile;
    private ArrayList<String> cities;
    private ArrayList<String> row;
    private ObservableList<String> countryList;

    public ParserFile() throws FileNotFoundException
    {
        this.configFile = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\infile.txt");
        this.cities = new ArrayList<String>();
        this.row = new ArrayList<String>();
        this.countryList = FXCollections.observableArrayList();
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public ObservableList<String> getCountryList() {
        return countryList;
    }

    public void parse() throws IOException
    {
        String city, country;
        HashSet<String> uniqueCountry = new HashSet<String>();

        row =(ArrayList<String>)Files.readAllLines(configFile.toPath(), Charset.defaultCharset());

        for(String line:row)
        {
            String[] value = line.split("/");
            city = value[1];
            country = value[4];

        //    cities.put(city, country);

            cities.add(country);
            cities.add(city);

            uniqueCountry.add(country);
        }

        for(String countryElement:uniqueCountry)
        {
            countryList.add(countryElement);
        }
    }
}
