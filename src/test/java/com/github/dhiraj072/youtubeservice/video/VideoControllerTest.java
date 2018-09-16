package com.github.dhiraj072.youtubeservice.video;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.google.api.services.youtube.model.Video;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
class VideoControllerTest {

  private YoutubeManager youtubeManager;
  private VideoController controller;

  @BeforeAll
  void setUp() {

    youtubeManager = Mockito.mock(YoutubeManager.class);
    controller = new VideoController(youtubeManager);
  }

  @Test
  void testGetRandomVideo() {

    Video randomVideo = new Video();
    randomVideo.setId("randomId");
    when(youtubeManager.getRandomYoutubeVideo()).thenReturn(randomVideo);
    assertEquals("randomId", controller.getRandomVideo().getId());
  }
}