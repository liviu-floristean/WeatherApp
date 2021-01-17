package ro.mta.se.lab;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Clasa de testare pentru ParserFile. Aceasta clasa testeaza
 * faptul ca parsarea fisierului de intrare se face corespunzator
 * si ca avem datele necesare popularii combobox-urilor prin care
 * utilizatorul va selecta ce doreste.
 *
 * @author Floriștean Liviu
 */

public class ParserFileTest {

    /**
     * Membrul acestei clase este o instanta a clasei ParserFile.
     */

    ParserFile parse;

    /**
     * In Aceasta metoda se initializeaza membrul clasei si se
     * afiseaza un mesaj care sa arate ca s-a executat cu succes.
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception {
        parse = new ParserFile();
        System.out.println("Before - done");
    }

    /**
     * Aceasta metoda arata ca s-a executat corect testarea.
     * @throws Exception
     */

    @After
    public void tearDown() throws Exception {
        System.out.println("After - done");
    }

    /**
     * Aceasta metoda verifica faptul ca s-au citit orasele din
     * fisierul de intrare. Deoarece aplicatia se bazeaza pe o
     * alegere a unui oras si a unei tari, testarea s-a bazat pe
     * faptul ca aplicatia citeste corect aceste date de intrare.
     * Prin urmare, se testeaza daca parametrul cities din clasa
     * ParserFile este null (nu s-a citit nimic, prin urmare nu
     * avem ce afisa in interfata).
     */

    @Test
    public void getCities() {
        assertNotNull(parse.getCities());
    }

    /**
     * Similar ca la metoda de mai sus, aici se verifica daca tarile
     * din fisierul de intrare au fost citite corespunzator si daca
     * se poate realiza rularea corespunzatoare a aplicatiei.
     */

    @Test
    public void getCountryList() {
        assertNotNull(parse.getCountryList());
    }
}