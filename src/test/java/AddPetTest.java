import base.BaseTest;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class AddPetTest extends BaseTest {
    @Test
    public void test01_AddPetWithAllData(){
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

       Pet result_pet = given()
               .when()
               .get("/pet/"+id)
               .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.category.getId(), equalTo(pet.category.getId()));
        assertThat(result_pet.category.getName(), equalTo(pet.category.getName()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet.photoUrls.get(0)));
        assertThat(result_pet.tags.size(), equalTo(pet.tags.size()));
        assertThat(result_pet.tags.get(0).getId(), equalTo(pet.tags.get(0).getId()));
        assertThat(result_pet.tags.get(0).getName(), equalTo(pet.tags.get(0).getName()));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
        assertThat(result_pet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test
    public void test02_IdMaxValue(){
        String id = "9223372036854775807";

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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet.photoUrls.get(0)));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
    }

    @Test
    public void test03_IdMinValue(){
        String id = "-9223372036854775808";

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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet.photoUrls.get(0)));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
    }

    @Test
    public void test04_IdLessThanMinValue(){
        String id = "-9223372036854775809";

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
                .statusCode(405);
    }

    @Test
    public void test05_IdMoreThanMaxValue(){
        String id = "9223372036854775808";

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
                .statusCode(405);
    }

    @Test
    public void test06_StringAsId(){
        String id = fake.address().cityName();

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
                .statusCode(405);
    }

    @Test
    public void test07_WithoutId(){
        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);
    }

    @Test
    public void test08_ShortName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.lorem().fixedString(1));
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
    }

    @Test
    public void test09_LongName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.lorem().fixedString(999999));
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
    }

    @Test
    public void test10_UkrainianName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName("їжак");
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getName(), equalTo(pet.getName()));
    }

    @Test
    public void test11_WithoutAName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setPhotoUrls(photoUrls);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test12_AddOnePhotoUrl(){
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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet.photoUrls.get(0)));
    }

    @Test
    public void test13_AddManyPhotoUrls(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        int i = 0;
        int photoUrls_size = fake.number().numberBetween(1000,5000);
        while(i < photoUrls_size){
            photoUrls.add(fake.internet().url());
            i++;
        }

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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.size(), equalTo(photoUrls_size));
        i = 0;
        while (i < photoUrls_size){
            assertThat(result_pet.photoUrls.get(i), equalTo(pet.photoUrls.get(i)));
            i++;
        }
    }

    @Test
    public void test14_AddEmptyPhotoUrl(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("");

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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.photoUrls.size(), equalTo(pet.photoUrls.size()));
        assertThat(result_pet.photoUrls.get(0), equalTo(pet.photoUrls.get(0)));
    }

    @Test
    public void test15_NoPhotoUrl(){
        String id = String.valueOf(fake.number().randomNumber());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test16_AddOneTag(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Tag tag = new Tag();
        tag.setId(String.valueOf(fake.number().randomNumber()));
        tag.setName(fake.harryPotter().character());

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.tags.size(), equalTo(pet.tags.size()));
        assertThat(result_pet.tags.get(0).getId(), equalTo(pet.tags.get(0).getId()));
        assertThat(result_pet.tags.get(0).getName(), equalTo(pet.tags.get(0).getName()));
    }

    @Test
    public void test17_AddManyTags(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        int i = 0;
        int tags_size = fake.number().numberBetween(1000,5000);
        ArrayList<Tag> tags = new ArrayList<>();

        while (i < tags_size){
            Tag tag = new Tag();
            tag.setId(String.valueOf(fake.number().randomNumber()));
            tag.setName(fake.harryPotter().character());

            tags.add(tag);

            i++;
        }


        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(result_pet.getId()));
        assertThat(result_pet.tags.size(), equalTo(pet.tags.size()));
        assertThat(result_pet.tags.size(), equalTo(tags_size));
        i = 0;
        while (i < tags_size){
            assertThat(result_pet.tags.get(i).getId(), equalTo(pet.tags.get(i).getId()));
            assertThat(result_pet.tags.get(i).getName(), equalTo(pet.tags.get(i).getName()));
            i++;
        }
    }

    @Test
    public void test18_TagWithoutId(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Tag tag = new Tag();
        tag.setName(fake.harryPotter().character());

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.tags.size(), equalTo(pet.tags.size()));
        assertThat(result_pet.tags.get(0).getId(), equalTo(null));
        assertThat(result_pet.tags.get(0).getName(), equalTo(pet.tags.get(0).getName()));
    }

    @Test
    public void test19_TagWithoutName(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Tag tag = new Tag();
        tag.setId(String.valueOf(fake.number().randomNumber()));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setTags(tags);

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.tags.size(), equalTo(pet.tags.size()));
        assertThat(result_pet.tags.get(0).getId(), equalTo(pet.tags.get(0).getId()));
        assertThat(result_pet.tags.get(0).getName(), equalTo(null));
    }

    @Test
    public void test20_NoTags(){
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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.tags, is(Matchers.<Tag>empty()));
    }

    @Test
    public void test21_SetStatusAvailable(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setStatus("available");

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test
    public void test22_SetStatusPending(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setStatus("pending");

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test
    public void test23_SetStatusSold(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setStatus("sold");

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test
    public void test24_SetInvalidStatus(){
        String id = String.valueOf(fake.number().randomNumber());

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(fake.internet().url());

        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(fake.harryPotter().character());
        pet.setPhotoUrls(photoUrls);
        pet.setStatus(fake.harryPotter().location());

        given()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test25_SetNoStatus(){
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

        Pet result_pet = given()
                .when()
                .get("/pet/"+id)
                .as(Pet.class);

        assertThat(result_pet.getId(), equalTo(pet.getId()));
        assertThat(result_pet.getStatus(), equalTo(null));
    }

    @Test
    public void test26_EmptyBody(){
        given()
                .contentType("application/json")
                .body("")
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test27_IncorrectJson(){
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": \n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"dog\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test28_WithoutBody(){
        given()
                .contentType("application/json")
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test29_GetInsteadOfPost(){
        given()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet")
                .then()
                .statusCode(405);
    }

    @Test
    public void test30_DeleteInsteadOfPost(){
        given()
                .contentType("application/json")
                .body("")
                .when()
                .delete("/pet")
                .then()
                .statusCode(405);
    }
}
