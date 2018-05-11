import base.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class DeletePetTest extends BaseTest {
    @Test
    public void test01_DeletePetWithAllData(){
        String id = String.valueOf(fake.number().randomNumber());

        Category category = new Category();
        category.setId(fake.number().randomNumber());
        category.setName(fake.harryPotter().character());

        Tag tag = new Tag();
        tag.setId(String.valueOf(fake.number().randomNumber()));
        tag.setName(fake.harryPotter().character());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Pet pet = new Pet();
        pet.setId(id);
        pet.setCategory(category);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);
        pet.setStatus("available");

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("pet/" + id)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .get("pet/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    public void test02_DeleteModifiedPetWithAllData(){
        String id = String.valueOf(fake.number().randomNumber());

        Category category1 = new Category();
        category1.setId(fake.number().randomNumber());
        category1.setName(fake.harryPotter().character());

        Tag tag1 = new Tag();
        tag1.setId(String.valueOf(fake.number().randomNumber()));
        tag1.setName(fake.harryPotter().character());

        ArrayList<String> photoUrls1 = new ArrayList<>();
        photoUrls1.add(fake.internet().url());

        ArrayList<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setCategory(category1);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls1);
        pet1.setTags(tags1);
        pet1.setStatus("available");

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Category category2 = new Category();
        category2.setId(fake.number().randomNumber());
        category2.setName(fake.harryPotter().character());

        Tag tag2 = new Tag();
        tag2.setId(String.valueOf(fake.number().randomNumber()));
        tag2.setName(fake.harryPotter().character());

        ArrayList<String> photoUrls2 = new ArrayList<>();
        photoUrls2.add(fake.internet().url());

        ArrayList<Tag> tags2 = new ArrayList<>();
        tags2.add(tag2);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setCategory(category1);
        pet2.setName(fake.harryPotter().character());
        pet2.setPhotoUrls(photoUrls2);
        pet2.setTags(tags2);
        pet2.setStatus("sold");

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("pet/" + id)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .get("pet/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    public void test03_DeleteNonExistingPet(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("pet/" + id)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("pet/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    public void test04_DeleteWithoutId(){
        String id = "";


        given()
                .contentType("application/json")
                .when()
                .delete("pet/"+ id)
                .then()
                .statusCode(405);
    }

    @Test
    public void test05_DeleteWithStringId(){
        String id = fake.artist().name();


        given()
                .contentType("application/json")
                .when()
                .delete("pet/"+ id)
                .then()
                .statusCode(400);
    }
}
