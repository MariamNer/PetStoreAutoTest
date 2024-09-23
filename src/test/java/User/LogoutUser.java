package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LogoutUser {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public void getMyInfoStatusCode() {
            User expectedUser = new User(
                    1,
                    "+79999999999",
                    0,
                    "12345",
                    "ivan@mail.ru",
                    "Petya",
                    "Иван",
                    "Иванов"
            );   // создаем юзера перед тестом
            UserApi.createUser(expectedUser);

            UserApi.loginUser(expectedUser.getUsername(), expectedUser.getPassword());

            given()
            .header("Content-type", "application/json")
            .get("/v2/user/logout")
            .then().log().all().statusCode(200);
    }
}
