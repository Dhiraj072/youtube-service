package com.github.dhiraj072.yrandom.youtubeservice;

import com.github.dhiraj072.yrandom.youtubeservice.exceptions.ConfigurationException;
import javax.annotation.PostConstruct;
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
  private String youtubeApiKey = "";

  @Value("${spring.application.name}")
  private String appName;

  @PostConstruct
  void init() throws ConfigurationException {

    setUpYoutubeApiKey();
  }

  private void setUpYoutubeApiKey() throws ConfigurationException {

    if (this.youtubeApiKey.isEmpty()) {

        this.youtubeApiKey = System.getenv(YOUTUBE_API_KEY);
      }
    if (this.youtubeApiKey == null || this.youtubeApiKey.isEmpty()) {

      throw new ConfigurationException("Invalid Youtube API key value = " + this.youtubeApiKey);
    }
  }

  public String getYoutubeApiKey() {

    return youtubeApiKey;
  }

  public String getAppName() {

    return appName;
  }
}
