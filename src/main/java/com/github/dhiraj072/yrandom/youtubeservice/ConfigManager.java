package com.github.dhiraj072.yrandom.youtubeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigManager {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ConfigManager.class);

  private static final String YOUTUBE_API_KEY = "YOUTUBE_API_KEY";

  @Value("${youtube.apikey}")
  private String youtubeApiKey;

  @Value("${spring.application.name}")
  private String appName;

  public String getYoutubeApiKey() {

    LOGGER.info("Youtube API key is {}", youtubeApiKey);
    if (youtubeApiKey == null || youtubeApiKey.isEmpty()) {

      if (System.getenv().containsKey(YOUTUBE_API_KEY) &&
          !System.getenv(YOUTUBE_API_KEY).isEmpty()) {

        youtubeApiKey = System.getenv(YOUTUBE_API_KEY);
        LOGGER.info("Youtube API key from env is {}", youtubeApiKey);
      } else {

        throw new IllegalArgumentException("Invalid Youtube API key value = " + youtubeApiKey);
      }
    }
    return youtubeApiKey;
  }

  public String getAppName() {

    return appName;
  }
}
