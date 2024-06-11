package utilities.API_Utilities;

import com.github.javafaker.Faker;
import config_Requirements.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Arrays;

import static hooks.HooksAPI.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class API_Methods {

    public static Response response;

    public static int id;
    public static String fullPath;
    public static Faker faker = new Faker();
    public static JSONObject requestBody;


    public static void pathParam(String rawPaths) {
        String[] paths = rawPaths.split("/");

        System.out.println(Arrays.toString(paths));

        StringBuilder tempPath = new StringBuilder("/{");


        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i;
            String value = paths[i].trim();

            spec.pathParam(key, value);

            tempPath.append(key + "}/{");

            if (value.matches("\\d+")) {  // value.matches("\\d+") burada value rakam iceriyorsa dedik
                id = Integer.parseInt(value);
            }
        }
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));

        fullPath = tempPath.toString();
        System.out.println("fullPath = " + fullPath);
        System.out.println("id : " + id);
    }

    public static String registerEmail() {
        spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url", "api")).build();
        spec.pathParams("pp1", "api", "pp2", "register");

        String password = faker.internet().password();

        requestBody = new JSONObject();
        requestBody.put("first_name", faker.name().firstName());
        requestBody.put("last_name", faker.name().lastName());
        requestBody.put("email", faker.internet().emailAddress());
        requestBody.put("password", password);
        requestBody.put("password_confirmation", password);
        requestBody.put("user_type", "customer");
        requestBody.put("referral_code", "0101010101");

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .body(requestBody.toString())
                .post("/{pp1}/{pp2}");

        JsonPath repJP = response.jsonPath();

        String email = repJP.getString("user.email");
        System.out.println("email = " + email);

        return email;
    }

    public static Response getResponse() {
        response = given()
                .spec(spec)
                .when()
                .get(fullPath);

        response.prettyPrint();

        return response;
    }

    public static Response getBodyResponse(Object requestBody) {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .get(fullPath);

        response.prettyPrint();

        return response;
    }

    public static Response postResponse(Object requestBody) {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .post(fullPath);

        response.prettyPrint();

        return response;
    }

    public static Response patchResponse(Object requestBody) {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .patch(fullPath);

        response.prettyPrint();

        return response;
    }

    public static Response deleteResponse(Object requestBody) {
        response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .delete(fullPath);

        response.prettyPrint();

        return response;
    }

    public static String tryCatchGet() {
        String exceptionMesaj = null;
        try {
            response = given()
                    .spec(spec)
                    .when()
                    .get(fullPath);
        } catch (Exception e) {
            exceptionMesaj = e.getMessage();
        }
        System.out.println("Exception Mesaj : " + exceptionMesaj);

        return exceptionMesaj;
    }

    public static String tryCatchGetBody(Object requestBody) {
        String exceptionMesaj = null;
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .when()
                    .body(requestBody)
                    .get(fullPath);
        } catch (Exception e) {
            exceptionMesaj = e.getMessage();
        }
        System.out.println("Exception Mesaj : " + exceptionMesaj);

        return exceptionMesaj;
    }

    public static String tryCatchDelete(Object requestBody) {
        String exceptionMesaj = null;
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .when()
                    .body(requestBody)
                    .delete(fullPath);
        } catch (Exception e) {
            exceptionMesaj = e.getMessage();
        }
        System.out.println("Exception Mesaj : " + exceptionMesaj);

        return exceptionMesaj;
    }

    public static String tryCatchPatch(Object requestBody) {
        String exceptionMesaj = null;
        try {
            response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .when()
                    .body(requestBody)
                    .patch(fullPath);
        } catch (Exception e) {
            exceptionMesaj = e.getMessage();
        }
        System.out.println("Exception Mesaj : " + exceptionMesaj);

        return exceptionMesaj;
    }

    public static void statusCodeAssert(int statusCode) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    public static void messageAssert(String message) {
        response.then()
                .assertThat()
                .body("message", equalTo(message));
    }
}
