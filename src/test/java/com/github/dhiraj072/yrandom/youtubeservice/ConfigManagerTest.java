package com.github.dhiraj072.yrandom.youtubeservice;

import com.github.dhiraj072.yrandom.youtubeservice.exceptions.ConfigurationException;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigManagerTest {

  @Rule
  public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

  private ConfigManager configManager;

  @BeforeEach
  void setUp() {

    configManager = new ConfigManager();
  }

  @Test
  void throwsWhenApiKeyNotSet() {

    environmentVariables.set("YOUTUBE_API_KEY", "");
    Assertions.assertThrows(ConfigurationException.class, () ->
        configManager.init());
  }
}