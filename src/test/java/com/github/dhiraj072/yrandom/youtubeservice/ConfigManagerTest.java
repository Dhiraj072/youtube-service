package com.github.dhiraj072.yrandom.youtubeservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.env.MockEnvironment;

@RunWith(MockitoJUnitRunner.class)
class ConfigManagerTest {

  private ConfigManager configManager;

  @Test
  @Disabled
  void throwsWhenApiKeyNotSet() {

    MockEnvironment environment = new MockEnvironment();
    environment.setProperty("YOUTUBE_API_KEY", "hello");
    Assertions.assertEquals("hello", System.getenv("YOUTUBE_API_KEY"));
    configManager = new ConfigManager();
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        configManager.getYoutubeApiKey());
  }
}