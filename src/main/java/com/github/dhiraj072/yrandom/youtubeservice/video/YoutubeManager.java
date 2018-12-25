package com.github.dhiraj072.yrandom.youtubeservice.video;

import com.github.dhiraj072.yrandom.youtubeservice.ConfigManager;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeManager {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(YoutubeManager.class);

  // Youtube API client service
  private static YouTube youtube;

  private ConfigManager configManager;

  @Autowired
  YoutubeManager(ConfigManager configManager) {

    try {

      LOGGER.info("Initializing youtube manager");
      this.configManager = configManager;
      if (configManager.getYoutubeApiKey().isEmpty()) {

        LOGGER.error("Youtube API Key is empty");
        throw new IllegalArgumentException("Youtube API Key is empty");
      }
      final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
      HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
          .setApplicationName(configManager.getAppName())
          .build();
    } catch (IOException | IllegalArgumentException | GeneralSecurityException e) {

      LOGGER.error("Unable to create YoutubeManager bean: {}", e);
    }
  }

  Video getRandomYoutubeVideo() {

    String videoId = "";
    try {

      YouTube.Search.List searchListByKeywordRequest = youtube.search()
          .list("snippet");
      searchListByKeywordRequest.setMaxResults((long) 1)
          .setQ("surfing")
          .setType("video")
          .setKey(configManager.getYoutubeApiKey());
      SearchListResponse response = searchListByKeywordRequest.execute();
      videoId = response.getItems().get(0).getId().getVideoId();
      LOGGER.debug("First video id {}", videoId);
      LOGGER.debug("Full response {}", response);
    } catch (GoogleJsonResponseException e) {

      e.printStackTrace();
      LOGGER.error("There was a service error: {} : {}",
          e.getDetails().getCode(), e.getDetails().getMessage());
    } catch (Throwable t) {

      t.printStackTrace();
    }

    return getVideoById(videoId);
  }

  private Video getVideoById(String videoId) {

    Video video = null;
    try {

      YouTube.Videos.List videosListByIdRequest =
          youtube.videos().list("snippet");
      videosListByIdRequest.setId(videoId).setKey(configManager.getYoutubeApiKey());
      VideoListResponse response = videosListByIdRequest.execute();
      video = response.getItems().get(0);
      LOGGER.info("VideoListResponse {}", response);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return video;
  }
}