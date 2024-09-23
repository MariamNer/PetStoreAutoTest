package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public void deleteUser(){
        // удаляет существующего пользователя
        User expectedUser = new User(
                2,
                "+79999999999",
                1,
                "123456",
                "anna@mail.ru",
                "Anna",
                "Анна",
                "Иванов"
        );   // создаем юзера перед тестом
        UserApi.createUser(expectedUser);

        given()
                .header("Content-type", "application/json")
                .delete("/v2/user/" + expectedUser.getUsername())
                .then().log().all()
                .statusCode(200);

        given()
                .header("Content-type", "application/json")
                .delete("/v2/user/" + expectedUser.getUsername())
                .then().log().all()
                .statusCode(404);
    }

}
