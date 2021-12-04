package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static ru.netology.data.RegistrationLocalHost.getRegisteredUser;
import static ru.netology.data.RegistrationLocalHost.getUser;
import static ru.netology.data.getRandomLogin;
import static ru.netology.data.getRandomPassword;

public class AuthTest {
    @BeforeEach
    public void shouldOpen() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldHappyPath() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id='login']  input").setValue(registeredUser.getLogin());
        $("[data-test-id='password']  input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    public void notActive() {
        var blockedUser = getRegisteredUser("blocked");

        $("[data-test-id='login']  input").setValue(blockedUser.getLogin());
        $("[data-test-id='password']  input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка!"));
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Пользователь заблокирован"));
    }

    @Test
    public void activeButNotReg() {
        var notRegisteredUser = getUser("active");

        $("[data-test-id='login']  input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password']  input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка!"));
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно " +
                "указан логин или пароль"));
    }

    @Test
    public void activeWithWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $("[data-test-id='login']  input").setValue(registeredUser.getLogin());
        $("[data-test-id='password']  input").setValue(wrongLogin);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка!"));
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно " +
                "указан логин или пароль"));
    }


    @Test
    public void activeWithWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $("[data-test-id='login']  input").setValue(registeredUser.getLogin());
        $("[data-test-id='password']  input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка!"));
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно " +
                "указан логин или пароль"));
    }
}
