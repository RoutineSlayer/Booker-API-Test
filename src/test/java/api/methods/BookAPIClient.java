package api.methods;

import api.models.BookInfo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class BookAPIClient {
    public Response getAllBooks() {
        return given().spec(requestSpecification).basePath("booking").get()
                .then().spec(responseSpecification).extract().response();
    }

    public BookInfo getBook(String id) {
        return given().spec(requestSpecification).basePath("booking/" + id).get()
                .then().spec(responseSpecification).extract().body().as(BookInfo.class);
    }
}
