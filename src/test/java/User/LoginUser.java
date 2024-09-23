package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class LoginUser {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test

    public void loginWithExistingUser(){
        User expectedUser = new User(
                1,
                "+79999999999",
                0,
                "1234",
                "ivan@mail.ru",
                "IVAN",
                "Иван",
                "Иванов"
        );   // создаем юзера перед тестом
        UserApi.createUser(expectedUser);
        given()
                .header("Content-type", "application/json")
                .header("api_key", "special-key")
                .get("/v2/user/login?username="+ expectedUser.getUsername() + "&password="+ expectedUser.getPassword())
                .then().log().all().statusCode(200);
    }

    @Test
    //передаем password, который не соответсвтвует указанному ранее username
    public void loginWrongPassword(){
        given()
                .header("Content-type", "application/json").header("api_key", "special-key")
                .get("/v2/user/login?username="+ Constant.USERNAME + "&password="+Constant.PASSWORD2)
                .then().log().all().statusCode(404);
    }

    @Test
    //пытаемся залогиниться с несуществующим username
    public void loginNotExistingUsername(){
        given()
                .header("Content-type", "application/json")
                .header("api_key", "special-key")
                .get("/v2/user/login?username="+ Constant.USERNAME2 + "&password="+Constant.PASSWORD)
                .then().log().all().statusCode(404);
    }

    @Test
    public void loginWithoutNeededParameters(){
        User expectedUser = new User(
                1,
                "+79999999999",
                0,
                "1234",
                "ivan@mail.ru",
                "IVAN",
                "Иван",
                "Иванов"
        );   // создаем юзера перед тестом
        UserApi.createUser(expectedUser);
        Response response =
                given()
                .header("Content-type", "application/json")
                .get("/v2/user/login");
                response.then().log().all();
        Assertions.assertNotEquals(200, response.statusCode());
    }
}
