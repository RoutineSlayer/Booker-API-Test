package api.book_api.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.*;

public final class Config {

    public static void initRestAssuredConfig() {
        baseURI = "https://restful-booker.herokuapp.com/";
        config = config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                new DefaultJackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type cls, String charset) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
                        return objectMapper;
                    }
                })).logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        requestSpecification = new RequestSpecBuilder().setConfig(config).setBaseUri(baseURI)
                .setContentType(ContentType.JSON).addFilter(new AllureRestAssured()).build();
        responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }
}
