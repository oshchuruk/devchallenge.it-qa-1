import base.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangeNameTest extends BaseTest {

    @Test
    public void test01_PutEditPetWithAllData(){
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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet2.getId()));
        assertThat(result_pet.category.getId(), equalTo(pet2.category.getId()));
        assertThat(result_pet.category.getName(), equalTo(pet2.category.getName()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet2.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet2.photoUrls.get(0)));
        assertThat(result_pet.tags.size(), equalTo(pet2.tags.size()));
        assertThat(result_pet.tags.get(0).getId(), equalTo(pet2.tags.get(0).getId()));
        assertThat(result_pet.tags.get(0).getName(), equalTo(pet2.tags.get(0).getName()));
        assertThat(result_pet.getName(), equalTo(pet2.getName()));
        assertThat(result_pet.getStatus(), equalTo(pet2.getStatus()));
    }

    @Test
    public void test02_PutShortName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setName(fake.lorem().fixedString(1));
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet2.getId()));
        assertThat(result_pet.getName(), equalTo(pet2.getName()));
    }

    @Test
    public void test03_PutLongName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setName(fake.lorem().fixedString(999999));
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet2.getId()));
        assertThat(result_pet.getName(), equalTo(pet2.getName()));
    }

    @Test
    public void test04_PutUkrainianName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setName("їжак");
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet2.getId()));
        assertThat(result_pet.getName(), equalTo(pet2.getName()));
    }

    @Test
    public void test05_PutWithoutAName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setName(null);
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test06_PutInvalidId(){
        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet2 = new Pet();
        pet2.setId(fake.harryPotter().character());
        pet2.setName(fake.harryPotter().character());
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void test07_PutNonExistingId(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet1 = new Pet();
        pet1.setId(id);
        pet1.setName(fake.harryPotter().character());
        pet1.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet1)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .delete("/pet/" + id)
                .then()
                .statusCode(200);

        Pet pet2 = new Pet();
        pet2.setId(id);
        pet2.setName(fake.harryPotter().character());
        pet2.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet2)
                .when()
                .post("/pet")
                .then()
                .statusCode(404);
    }

}
