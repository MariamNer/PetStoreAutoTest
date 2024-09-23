package User;

import Api.UserApi;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateWithArray {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public void createTwoUsers() {
        // создаю 2 юзеров
        User expectedUser_1 = new User(
                2,
                "+79999999999",
                1,
                "123456",
                "anna@mail.ru",
                "Anna",
                "Анна",
                "Иванов"
        );
        User expectedUser_2 = new User(
                3,
                "+79999999988",
                1,
                "1111",
                "tst@mail.ru",
                "test",
                "test",
                "test"
        );
        // сериализуем созданные POJO объекты
        JSONObject jsonObject_1 = new JSONObject(expectedUser_1);
        JSONObject jsonObject_2 = new JSONObject(expectedUser_2);
        // создаем массив и добавлем элементы в него
        JSONArray jsonBody = new JSONArray();
        jsonBody.put(jsonObject_1);
        jsonBody.put(jsonObject_2);

        //
        Response response = given().header("Content-type", "application/json")
                .and()
                .body(jsonBody.toString())
                .when().log().all()
                .post("/v2/user/createWithArray");
        response.then().log().all()
                .assertThat().body("message", equalTo("ok")) // сраниваем message  в ответе, что вернул "ok"
                .and()
                .statusCode(200);

        // если пользователь действительно создался, то мы сможем его успешно удалить
        given()
                .header("Content-type", "application/json")
                .delete("/v2/user/" + expectedUser_1.getUsername())
                .then().log().all()
                .statusCode(200);

        given()
                .header("Content-type", "application/json")
                .delete("/v2/user/" + expectedUser_2.getUsername())
                .then().log().all()
                .statusCode(200);
    }

}
