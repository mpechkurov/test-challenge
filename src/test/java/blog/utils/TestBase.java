package blog.utils;

import java.util.Properties;

import io.restassured.RestAssured;

public class TestBase {

    protected static final String ROOT_ELEMENT = ".";
    protected static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private Properties properties;

    public TestBase() {
        properties = new PropertiesLoader().load();
        RestAssured.baseURI = properties.getProperty("service.url");
    }
}
