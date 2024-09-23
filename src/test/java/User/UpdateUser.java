package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



public class UpdateUser {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";    // задаем хостнейм
    }

    @Test
    public void updateUser() {
        User expectedUser = new User(
                2,
                "+79999998888",
                1,
                "12345679",
                "test@mail.ru",
                "Test",
                "Тест",
                "Тест"
        );   // создаем юзера перед тестом
        UserApi.createUser(expectedUser);

        User updateedUser = expectedUser;
        updateedUser.setId(10);
        JSONObject bodyJson = new JSONObject(updateedUser);
        // создаем строку, которую далее превратим в джсон в теле запроса
        Response response = given().header("Content-type", "application/json")
                .and()
                .body(bodyJson.toString())
                .when().log().all()
                .put("/v2/user/" + expectedUser.getUsername());
        response.then().log().all()
                .assertThat().body("message", equalTo(String.valueOf(updateedUser.getId()))) // сраниваем id  с message  в ответе
                .and()
                .statusCode(200);
    }
}
