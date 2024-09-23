package Pet;

import dto.CategoryPet;
import dto.Pet;
import dto.TagsPet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class CreatePet {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";    // задаем хостнейм
    }

    @ParameterizedTest
    @ValueSource(longs = {-1,0,1, Long.MIN_VALUE, Long.MAX_VALUE })  // задаем граничные и окологранчиные параметры id
    public void createNewPetId(long id) {
        Pet pet = new Pet(
                id,
                new CategoryPet(0, "dog"),
                "doggie",
                new String[0],
                new TagsPet[]{ new TagsPet(0, "string") },
                "available"
        );
        JSONObject jsonObject = new JSONObject(pet);
        Response response = given().header("Content-type", "application/json")
                .header("api_key","special-key")
                .and()
                .body(jsonObject.toString())
                .when().log().all()
                .post("/v2/pet");

        Pet actualPet =
                response.then().log().all()     // Десериализруем: создаем pojo объект типа Pet из json'а в ответе
                .assertThat()
                .statusCode(200)
                .and()
                .extract().body().as(Pet.class);
        Assertions.assertEquals(actualPet.toString(), pet.toString());  // сравниваем строки созданного и отправленного объектов
    }

}
