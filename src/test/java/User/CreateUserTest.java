package User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUserTest {

    @BeforeAll
    public static void setUP() {
        RestAssured.baseURI = "https://petstore.swagger.io";    // задаем хостнейм
    }

    @ParameterizedTest
    @ValueSource(longs = {-1,0,1, Long.MIN_VALUE, Long.MAX_VALUE})  // задаем граничные и окологранчиные параметры id
    public void createNewUserId(long id) {
        String bodyJson = "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"username\":\"" + Constant.USERNAME + "\",\n" +
                "  \"firstName\": \"Иван\",\n" +
                "  \"lastName\": \"Иванов\",\n" +
                "  \"email\": \"ivan@mail.ru\" ,\n" +
                "  \"password\":\"" + Constant.PASSWORD + "\",\n" +
                "  \"phone\": \"+79999999999\",\n" +
                "  \"userStatus\": 0\n" +
                "}"; // создаем строку, которую далее превратим в джсон в теле запроса
        Response response = given().header("Content-type", "application/json")
                .and()
                .body(bodyJson)
                .when().log().all()
                .post("/v2/user");
        response.then().log().all()
                .assertThat().body("message", equalTo(String.valueOf(id))) // сраниваем id  с message  в ответе
                .and()
                .statusCode(200);
    }
//    @Test
//    public void createNewUserTwice(){
//        Integer id = 0;
//        String bodyJson = "{\n" +
//                "  \"id\": " + id + ",\n" +
//                "  \"username\": \"IVAN\",\n" +
//                "  \"firstName\": \"Иван\",\n" +
//                "  \"lastName\": \"Иванов\",\n" +
//                "  \"email\": \"ivan@mail.ru\" ,\n" +
//                "  \"password\": \"1234\",\n" +
//                "  \"phone\": \"+79999999999\",\n" +
//                "  \"userStatus\": 0\n" +
//                "}";
//        Response response = given().header("Content-type", "application/json").header("api_key", "special-key")
//                .and()
//                .body(bodyJson)
//                .when()
//                .post("/v2/user");
//        response.then().log().all()
//                .assertThat().body("message", equalTo(String.valueOf(id)))
//                .and()
//                .statusCode(200);
//    }
}