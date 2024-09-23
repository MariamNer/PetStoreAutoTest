package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;
import static org.hamcrest.Matchers.equalTo;

public class SearchUser {

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
                "1234",
                "ivan@mail.ru",
                "IVAN",
                "Иван",
                "Иванов"
        );   // создаем юзера перед тестом
        UserApi.createUser(expectedUser);
                given()
                .header("Content-type", "application/json")
                .get("/v2/user/"+ expectedUser.getUsername())   //дергаем ручку по имени созданного пользователя
                .then().log().all()
                .assertThat()
                    .statusCode(200) // проверяем, что вернулся в ответе статус кода 200 ок
                    .and()
                    .body(    "id", Matchers.equalTo(expectedUser.getId()),
                            "username", Matchers.equalTo(expectedUser.getUsername()),
                            "firstName", Matchers.equalTo(expectedUser.getFirstName()),
                            "lastName", Matchers.equalTo(expectedUser.getLastName()),
                            "email", Matchers.equalTo(expectedUser.getEmail()),
                            "password", Matchers.equalTo(expectedUser.getPassword()),
                            "phone", Matchers.equalTo(expectedUser.getPhone()),
                            "userStatus", Matchers.equalTo(expectedUser.getUserStatus())
                    );
                // проверяем, что в теле ответа вернулся созданный юзер
    }
}
