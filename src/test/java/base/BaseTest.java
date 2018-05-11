package base;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;


public class BaseTest {
    protected Faker fake = new Faker();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = "http://petstore.swagger.io/v2";
    }
}
