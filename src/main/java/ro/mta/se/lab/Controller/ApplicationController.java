package ro.mta.se.lab.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String, String> cityList;
    ObservableList<String> countryList;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> cityComboBox;

    public ApplicationController() throws IOException {

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

    }
}
