package com.github.dhiraj072.yrandom.youtubeservice.video;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class VideoControllerRestAPITest {

  @LocalServerPort
  private int serverPort;

  @BeforeAll
  void setUp() {

    RestAssured.port = serverPort;
  }

  @Test
  void testReturnsAGoodRandomVideo() {

    when().get("/youtube/video/random").then()
        .statusCode(200)
        .assertThat().body(matchesJsonSchemaInClasspath("RestAPI/video.json"));
  }
}
