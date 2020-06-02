package org.karthikps.testautomation.infra;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.tests.ApiTest;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

    public static Properties testProperties;
    private static final Logger logger = LogManager.getLogger(TestProperties.class);

    public static void LoadProperties() throws IOException {
        testProperties = new Properties();
        InputStream inputStream = null;
        FileReader fileReader = null;
        try {
            inputStream = TestProperties.class.getClassLoader().getResourceAsStream("testconfig.properties");
        }
        catch (Exception e) {
            logger.warn("Properties file not found in resources: " + e.getMessage());
            fileReader = new FileReader("/var/jenkins_home/testconfig.properties");
        }

        if(inputStream != null ) {
            testProperties.load(inputStream);
        }
        else if(fileReader != null){
            testProperties.load(fileReader);
        }
    }

    public static String getPropertyValue(String propertyName) {
        return testProperties.getProperty(propertyName);
    }
}
