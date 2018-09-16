package com.github.dhiraj072.youtubeservice.video;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class VideoControllerRestAPITest {

  @Test
  void testReturnsAGoodRandomVideo() {

    when().get("/youtube/video/random").then()
        .statusCode(200)
        .assertThat().body(matchesJsonSchemaInClasspath("RestAPI/video.json"));
  }
}
