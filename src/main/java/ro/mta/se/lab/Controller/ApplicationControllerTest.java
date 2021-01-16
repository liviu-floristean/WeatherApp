package ro.mta.se.lab.Controller;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ApplicationControllerTest {
//    @RunWith(JfxRunner.class)

    ApplicationController controller;
    @Before
    public void setUp() throws Exception {
        controller = new ApplicationController();
        System.out.println("Before done");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("After done");
    }

    @Test
    public void chooseCountry() {
        assertNotNull(controller.getCountryList());
    }

    @Test
    public void chooseCity() {
        assertNotNull(controller.getCityList());
        assertNotNull(controller.getCountryList());
    }
}