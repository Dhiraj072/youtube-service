package com.github.dhiraj072.yrandom.youtubeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigManager {

  @Value("${youtube.apikey}")
  private String youtubeApiKey;

  @Value("${spring.application.name}")
  private String appName;

  public String getYoutubeApiKey() {

    if (youtubeApiKey.isEmpty()) {

      youtubeApiKey = System.getenv("YOUTUBE_API_KEY");
    }
    return youtubeApiKey;
  }

  public String getAppName() {

    return appName;
  }
}
