package Pet;

import Api.PetApi;
import dto.CategoryPet;
import dto.Pet;
import dto.StatusEnum;
import dto.TagsPet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SearchPetByStatusTest {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";    // задаем хостнейм
    }

    @Test
    public void searchPetByStatus() {
        Pet expectedPet = new Pet(
                3L,
                new CategoryPet(0, "dog"),
                "doggie",
                new String[0],
                new TagsPet[]{new TagsPet(0, "string")},
                "available"
        );
        Pet expectedPet_1 = new Pet(
                4L,
                new CategoryPet(0, "cat"),
                "doggie",
                new String[0],
                new TagsPet[]{new TagsPet(0, "string")},
                "sold"
        );
        Pet expectedPet_2 = new Pet(
                7L,
                new CategoryPet(0, "cat"),
                "doggie",
                new String[0],
                new TagsPet[]{new TagsPet(0, "string")},
                "pending"
        );

        // создаем питомцов с разными статусами перед тестом
        PetApi.createPet(expectedPet);
        PetApi.createPet(expectedPet_1);
        PetApi.createPet(expectedPet_2);
        // сохраняем в перемнную response ответ
        Response response =
                given()
                .header("Content-type", "application/json")
                .header("api_key", "special-key").log().all()
                .get("/v2/pet/findByStatus?status=" + StatusEnum.SOLD.getTextValue());
        // проверяем, что статус код пришел 200
        response
                .then().log().all().statusCode(200);
        // создаем массив, в который сохраняем тело ответа
        JSONArray responseBody = new JSONArray(response.getBody().asString());
        // проверяем, что в ответе есть объекты
        Assertions.assertFalse(responseBody.isEmpty());
        // проверяем, что у каждого питомца статус соответсвует статусу sold
        for (int i = 0; i < responseBody.length(); i++ ) {
        Assertions.assertEquals(
                StatusEnum.SOLD.getTextValue(),
                responseBody.getJSONObject(i).get("status")
        );
        }

    }
}
