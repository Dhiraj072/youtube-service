package com.github.dhiraj072.yrandom.youtubeservice.video;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.dhiraj072.yrandom.youtubeservice.ConfigManager;
import com.github.dhiraj072.yrandom.youtubeservice.exceptions.ConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
public class YoutubeManagerTest {

  private ConfigManager configManager;

  @Test
  void throwsOnInvalidApiKey() {

    configManager = Mockito.mock(ConfigManager.class);
    Mockito.when(configManager.getYoutubeApiKey()).thenThrow(IllegalArgumentException.class);
    assertThrows(ConfigurationException.class, () -> {
      new YoutubeManager(configManager);
    });
  }
}
