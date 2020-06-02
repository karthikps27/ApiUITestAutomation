package org.karthikps.testautomation.tests;

import org.karthikps.testautomation.infra.TestProperties;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class SetupTest {

    @BeforeSuite
    public void setupTests() throws IOException {
        TestProperties.LoadProperties();
    }
}
