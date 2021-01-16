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

/**
 * Aceasta clasa implementeaza o metoda de parsare
 * a fisierului de intrare in care se afla tarile
 * cu orasele corespunzatoare.
 *
 * @author Floriștean Liviu
 */

public class ParserFile {

    /**
     * Descrierea membrilor clasei:
     *
     * <b>configFile</b> este un membru de tip File
     * folosit pentru preluarea datelor fisierului de
     * intrare.
     *
     * <b>cities</b> este un membru de tip ArrayList
     * populat cu obiecte de tip String. In urma parsarii
     * fisierului de intrare, aici se vor retine orasele.
     *
     * <b>row</b> este un membru de tip ArrayList populat
     * cu obiecte de tip String. Fiecare element este o
     * linie din fisierul de intrare, care contine informatii
     * despre un oras. Elementele acestui membru vor fi
     * parsate ulterior pentru a se obtine orasul si tara.
     *
     * <b>countryList</b> este un obiect de tip ObservableList
     * in care se vor retine toate tarile gasite in fisierul de
     * intrare si mai departe vor fi pasate clasei de control
     * pentru popularea unui combobox.
     */
    private File configFile;
    private ArrayList<String> cities;
    private ArrayList<String> row;
    private ObservableList<String> countryList;

    /**
     * Constructorul clasei ParserFile
     * @throws FileNotFoundException
     */

    public ParserFile() throws FileNotFoundException
    {
        this.configFile = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\ro\\mta\\se\\lab\\infile.txt");
        this.cities = new ArrayList<String>();
        this.row = new ArrayList<String>();
        this.countryList = FXCollections.observableArrayList();
    }

    /**
     * Getter care returneaza membrul <b>cities</b>
     * @return cities
     */

    public ArrayList<String> getCities() {
        return cities;
    }

    /**
     * Getter care returneaza membrul <b>countryList</b>
     * @return countryList
     */

    public ObservableList<String> getCountryList() {
        return countryList;
    }

    /**
     * Functia de parsare a fisierului de intrare. Aceasta
     * citeste fisierul linie cu linie si il parseaza in
     * functie de caracterul "/", care delimiteaza fiecare
     * element dintr-o linie. Se retin numele orasului si
     * numele tarii de pe fiecare linie. Numele orasului va fi
     * adaugat membrului <b>cities</b>. Pentru numele tarilor,
     * s-a folosit un HashSet pentru a se retine doar o data
     * aparitia unei tari in tot fisierul de intrare. Ulterior,
     * elementele din HashSet au fost transmise membrului
     * <b>countryList</b>
     * @throws IOException
     */

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
