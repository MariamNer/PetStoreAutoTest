package Api;

import dto.User;
import io.restassured.response.Response;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.json.JSONObject;

public class UserApi {
    public static void createUser(User user){
            JSONObject jsonObject = new JSONObject(user);
            Response response = given()
                    .header("Content-type", "application/json")
                    .header("api_key", "special-key")
                    .and()
                    .body(jsonObject.toString())
                    .when().log().all()
                    .post("/v2/user");
            response.then().log().all()
                    .assertThat().body("message", equalTo(String.valueOf(user.getId())))
                    .and()
                    .statusCode(200);
        }

    public static void loginUser (String username, String password){
                given()
                .header("Content-type", "application/json")
                .header("api_key", "special-key")
                .get("/v2/user/login?username="+ username + "&password="+ password)
                .then().log().all().statusCode(200);

    }
}

