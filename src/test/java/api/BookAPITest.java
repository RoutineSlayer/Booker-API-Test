package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;
import model.BookInfo;
import model.BookingDates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class BookAPITest {
    private final static String BASE_URL = "https://restful-booker.herokuapp.com/";
    private final static String token = "Basic YWRtaW46cGFzc3dvcmQxMjM=";

    static {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(new DefaultJackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type cls, String charset) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
                        return objectMapper;
                    }
                }));
    }

    @Test
    void getAllBookIDsReturn200() {
        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get(BASE_URL + "booking")
                .then().log().body().statusCode(200);
    }

    @Test
    void getBookByIdReturn200() {
        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get(BASE_URL + "booking/1")
                .then().log().body().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void getBooksReturn200(String id) {
        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get(BASE_URL + "booking/" + id)
                .then().log().body().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-2", "-4"})
    void getNonExistentBooksReturn404(String id) {
        RestAssured
                .given().contentType(ContentType.JSON)
                .when().get(BASE_URL + "booking/" + id)
                .then().log().body().statusCode(404);
    }

    @Test
    void postBookReturn200() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn(LocalDate.now().toString());
        bookingDates.setCheckOut(LocalDate.now().plusDays(7).toString());

        BookInfo book = new BookInfo();
        book.setFirstName("Анна");
        book.setLastName("Сидорова");
        book.setTotalPrice(200);
        book.setDepositPaid(false);
        book.setBookingDates(bookingDates);
        book.setAdditionalNeeds("Needs");

        RestAssured
                .given().contentType(ContentType.JSON).body(book)
                .when().post(BASE_URL + "booking")
                .then().log().body().statusCode(200).extract().body();
    }

    @Test
    void deleteBookReturn201() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn(LocalDate.now().toString());
        bookingDates.setCheckOut(LocalDate.now().plusDays(7).toString());

        BookInfo book = new BookInfo();
        book.setFirstName("Анна");
        book.setLastName("Сидорова");
        book.setTotalPrice(200);
        book.setDepositPaid(false);
        book.setBookingDates(bookingDates);
        book.setAdditionalNeeds("Needs");

        int bookingId = RestAssured
                .given().contentType(ContentType.JSON).body(book)
                .when().post(BASE_URL + "booking")
                .then().log().body().extract().jsonPath().getInt("bookingid");

        RestAssured
                .given().contentType(ContentType.JSON).header("Authorization", token)
                .when().delete(BASE_URL + "booking/" + bookingId)
                .then().log().body().statusCode(201);
    }


}
