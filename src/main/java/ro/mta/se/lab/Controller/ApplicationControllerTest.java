package ro.mta.se.lab.Controller;


import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ApplicationControllerTest {
//    @RunWith(JfxRunner.class)

    ApplicationController controller;
    @org.junit.Before
    public void setUp() throws Exception {
        controller = new ApplicationController();
        System.out.println("Before done");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("After done");
    }

    @org.junit.Test
    public void chooseCountry() {
        assertNotNull(controller.getCountryComboBox());
    }

    @org.junit.Test
    public void chooseCity() {
        assertNotNull(controller.getCityComboBox());
        assertNotNull(controller.getCountryComboBox());
    }
}