package com.github.dhiraj072.youtubeservice.video;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YoutubeManagerIntegrationTest {

  @Autowired
  YoutubeManager youtubeManager;

  @Test
  void testReturnAGoodRandomVideo() {

    assertNotNull(youtubeManager.getRandomYoutubeVideo());
    assertNotNull(youtubeManager.getRandomYoutubeVideo().getId());
  }

}