package com.galen.framework;


import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class GalenFirstTest {
    private static final String TestsigmaLayoutPage = "/Users/amits/eclipse-workspace/GalenProject/GelenSpecs/specfile.gspec";
    private static final String baseURL="https://www.orangehrm.com/";
    private WebDriver driver;
    private LayoutReport layoutReport;

   
    @BeforeClass
    public void init() {
       
    	WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
    }
    @Test
    public void checkLogoVisibleAndImageVerification() throws IOException {
       
        layoutReport = Galen.checkLayout(driver, TestsigmaLayoutPage, Arrays.asList("desktop"));
    }
    @AfterMethod
    public void reportUpdate() {
        try {
            //Creating a list of tests
            List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
            //The object you create will be consisting the information regarding the test
            GalenTestInfo test = GalenTestInfo.fromString("Test Automation Using Galen Framework");
           
            test.getReport().layout(layoutReport, "Verify logo present and log image comparison");
            tests.add(test);
            //Exporting all test report to html
            new HtmlReportBuilder().build(tests, "/Users/amits/eclipse-workspace/GalenProject/src/main/resources");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
      
        driver.quit();
    }

}
