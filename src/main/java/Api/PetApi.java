package Api;

import dto.Pet;
import dto.User;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetApi {

    public static void createPet(Pet pet) {
        JSONObject jsonObject = new JSONObject(pet);
        Response response = given()
                .header("Content-type", "application/json")
                .header("api_key", "special-key")
                .and()
                .body(jsonObject.toString())
                .when().log().all()
                .post("/v2/pet");
        response.then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
