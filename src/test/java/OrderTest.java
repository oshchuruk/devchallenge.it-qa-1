import base.BaseTest;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;


public class OrderTest extends BaseTest {
    @Test
    public void test01_CreateOrderWithAllDataAndFalse(){
        String id = String.valueOf(fake.number().randomNumber());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = String.valueOf(df.format(fake.date().birthday()));

        Order order = new Order();
        order.setId(id);
        order.setPetId(String.valueOf(fake.number().randomNumber()));
        order.setQuantity(String.valueOf(fake.number().randomNumber()));
        order.setShipDate(date);
        order.setStatus("placed");
        order.setComplete(false);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);

        Order result_order = given()
                .when()
                .get("/store/order/"+id)
                .as(Order.class);

        assertThat(result_order, samePropertyValuesAs(order));
    }

    @Test
    public void test02_CreateOrderWithoutDateAndTrue(){
        String id = String.valueOf(fake.number().randomNumber());

        Order order = new Order();
        order.setId(id);
        order.setPetId(String.valueOf(fake.number().randomNumber()));
        order.setQuantity(String.valueOf(fake.number().randomNumber()));
        order.setStatus("approved");
        order.setComplete(true);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);

        Order result_order = given()
                .when()
                .get("/store/order/"+id)
                .as(Order.class);

        assertThat(result_order, samePropertyValuesAs(order));
    }

    @Test
    public void test02_CreateOrderWithInvalidData(){
        String id = fake.artist().name();

        String date = fake.cat().breed();

        Order order = new Order();
        order.setId(id);
        order.setPetId(fake.harryPotter().character());
        order.setQuantity(fake.name().fullName());
        order.setShipDate(date);
        order.setStatus(fake.beer().name());
        order.setComplete(true);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(400);
    }

    @Test
    public void test02_DeleteOrderWithAllData(){
        String id = String.valueOf(fake.number().randomNumber());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = String.valueOf(df.format(fake.date().birthday()));

        Order order = new Order();
        order.setId(id);
        order.setPetId(String.valueOf(fake.number().randomNumber()));
        order.setQuantity(String.valueOf(fake.number().randomNumber()));
        order.setShipDate(date);
        order.setStatus("placed");
        order.setComplete(false);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("/store/order/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void test03_DeleteNonExistingOrder(){
        String id = String.valueOf(fake.number().randomNumber());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = String.valueOf(df.format(fake.date().birthday()));

        Order order = new Order();
        order.setId(id);
        order.setPetId(String.valueOf(fake.number().randomNumber()));
        order.setQuantity(String.valueOf(fake.number().randomNumber()));
        order.setShipDate(date);
        order.setStatus("placed");
        order.setComplete(false);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("/store/order/" + id)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("/store/order/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    public void test04_DeleteInvalidIdOrder(){
        String id = fake.harryPotter().character();

        given()
                .contentType("application/json")
                .when()
                .delete("/store/order/" + id)
                .then()
                .statusCode(400);
    }
}
