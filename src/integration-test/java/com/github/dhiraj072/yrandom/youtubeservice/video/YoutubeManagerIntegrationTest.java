package com.github.dhiraj072.yrandom.youtubeservice.video;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.dhiraj072.yrandom.youtubeservice.ConfigManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * This test requires a valid youtube api key to be set.
 * See {@link ConfigManager#getYoutubeApiKey()}
 */
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