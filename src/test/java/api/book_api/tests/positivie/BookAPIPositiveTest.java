package api.book_api.tests.positivie;

import api.book_api.client.models.request.BookingDates;
import api.book_api.client.models.request.BookingRequest;
import api.book_api.client.models.response.BookingIDResponse;
import api.book_api.client.models.response.BookingResponse;
import api.book_api.client.models.response.CreateBookingResponse;
import api.book_api.client.positive.BookAPIPositiveClient;
import api.book_api.tests.Config;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookAPIPositiveTest {
    private static BookingDates bookingDates;
    private static BookingRequest bookingRequest;
    private static BookingResponse bookingResponse;
    private static CreateBookingResponse createBookingResponse;
    private static String USERNAME;
    private static String PASSWORD;

    private BookAPIPositiveClient bookAPIPositiveClient;
    private Dotenv dotenv;

    @BeforeEach
    void setUp() {
        dotenv = Dotenv.load();
        bookAPIPositiveClient = new BookAPIPositiveClient();
        Config.initRestAssuredConfig();
    }


    @Owner("Я НАПИСАЛ, но если меня уволят, ТО КТО ОВНЕР?")
    @Feature("Booking API")
    @DisplayName("Проверка получения списка всех Booking Id")
    @Story("Клиент должен иметь возможность получить список всех Booking Id")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void getAllBookingIds_CheckTheSizeIsOver10_ReturnTrue() {
        AtomicReference<List<BookingIDResponse>> bookingIds = new AtomicReference<>(new ArrayList<>());
        step("Получаем список всех Booking ID", () -> {
            bookingIds.set(bookAPIPositiveClient.getAllBookingIds());
        });

        step("Проверяем что размер списка больше или равен 10", () -> {
            assertThat(bookingIds.get().size(), allOf(notNullValue(), greaterThanOrEqualTo(10)));
        });
    }

    @Owner("Я НАПИСАЛ, но если меня уволят, ТО КТО ОВНЕР?")
    @Feature("Booking API")
    @DisplayName("Проверка получения Booking")
    @Test
    void getBookingById_CheckFirstNameIsNotEmpty_ReturnTrue() {
        step("Отправляем запрос на получения Booking", () -> {
            bookingResponse = bookAPIPositiveClient.getBooking("2");
        });

        step("Проверяем, что имя полученного Booking не пустое", () -> {
            assertThat(bookingResponse.firstName(), allOf(notNullValue(), not(emptyString())));
        });
    }

    @Feature("Booking API")
    @DisplayName("Проверка получения списка всех Booking Id")
    @ParameterizedTest
    @CsvSource({"4", "2", "3"})
    void getBookingById_CheckFirstNameIsNotEmptyMix_ReturnTrue(String bookingId) {
        step("Отправляем запрос на получения Booking по id", () -> {
            bookingResponse = bookAPIPositiveClient.getBooking(bookingId);
        });


        step("Проверяем, что имя полученного Booking по id не пустое", () -> {
            assertThat(bookingResponse.firstName(), allOf(notNullValue(), not(emptyString())));
        });
    }

    @Feature("Booking API")
    @DisplayName("Проверка создания Booking с валидными данными")
    @Test
    void createBooking_WithValidData_ReturnTrue() {

        step("Создаем объект Booking для отправки запроса на сервер", () -> {
            bookingDates = new BookingDates(
                    LocalDate.now().toString(),
                    LocalDate.now().plusDays(7).toString()
            );

            bookingRequest = new BookingRequest(
                    "Анна",
                    "Сидорова",
                    200,
                    false,
                    bookingDates,
                    "Needs"
            );

        });
        step("Отправка запроса на создание Booking", () -> {
            createBookingResponse = bookAPIPositiveClient.createBooking(bookingRequest);
        });
        step("Проверяем, что имя в ответе, совпадает с отправленным в запросе", () -> {
            assertThat(createBookingResponse.bookingId(), notNullValue());
            assertThat(createBookingResponse.bookingResponse().firstName(), equalTo("Анна"));
        });

    }

    @Feature("Booking API")
    @DisplayName("Проверка удаления Booking с валидными данными")
    @Test
    void deleteBooking_WithValidBookingId_ReturnTrue() {


        step("Создаем объект Booking для отправки запроса на сервер", () -> {
            bookingDates = new BookingDates(
                    LocalDate.now().toString(),
                    LocalDate.now().plusDays(7).toString()
            );
            bookingRequest = new BookingRequest(
                    "Анна",
                    "Сидорова",
                    200,
                    false,
                    bookingDates,
                    "Needs"
            );
        });
        step("Получаем из окружения логин и пароль для авторизации", () -> {
            USERNAME = dotenv.get("USER_NAME");
            PASSWORD = dotenv.get("PASSWORD");
        });
        step("Создаем Booking", () -> {
            createBookingResponse = bookAPIPositiveClient.createBooking(bookingRequest);
        });
        step("Удаляем созданный Booking", () -> {
            bookAPIPositiveClient.deleteBooking(USERNAME, PASSWORD, createBookingResponse.bookingId());
        });
        step("Получаем нформацию по удаленному Booking, ожидаем Exception", () -> {
            try {
                bookAPIPositiveClient.getBooking(createBookingResponse.bookingId());
                assertThat(bookingResponse.firstName(), is(nullValue()));
            } catch (Exception exception) {
                assertThat(exception.getMessage(), is(not(emptyString())));
            }
        });
    }
}
