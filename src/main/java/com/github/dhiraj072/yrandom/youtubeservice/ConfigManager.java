package com.github.dhiraj072.yrandom.youtubeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigManager {

  @Value("${client.secret}")
  private String clientSecret;

  @Value("${spring.application.name}")
  private String appName;

  public String getClientSecret() {

    return clientSecret;
  }

  public String getAppName() {

    return appName;
  }
}
