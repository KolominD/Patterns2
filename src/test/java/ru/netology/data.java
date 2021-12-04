package ru.netology;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class data {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendRequest(User userInfo) {
        given()
                .spec(requestSpec)
                .body(userInfo)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private data() {
    }

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class RegistrationLocalHost {
        private RegistrationLocalHost() {
        }

        public static User getUser(String status) {
            return new User(getRandomLogin(), getRandomPassword(), status);
        }

        public static User getRegisteredUser(String status) {
            User registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class User {
        String login;
        String password;
        String status;
    }
}