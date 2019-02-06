package com.github.dhiraj072.yrandom.youtubeservice.video;

import com.github.dhiraj072.yrandom.youtubeservice.ConfigManager;
import com.github.dhiraj072.yrandom.youtubeservice.exceptions.ConfigurationException;
import com.github.dhiraj072.yrandom.youtubeservice.utils.DataMuseRandomWordGenerator;
import com.github.dhiraj072.yrandom.youtubeservice.utils.RandomWordGenerator;
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

  private YouTube youtube;
  private String youtubeApiKey;
  private RandomWordGenerator randomWordGenerator;

  @Autowired
  private YoutubeManager(ConfigManager configManager) throws ConfigurationException {

    try {

      this.youtubeApiKey = configManager.getYoutubeApiKey();
      this.randomWordGenerator = new DataMuseRandomWordGenerator();
      final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
      HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
          .setApplicationName(configManager.getAppName())
          .build();
    } catch (IOException | IllegalArgumentException | GeneralSecurityException e) {

      LOGGER.error("Unable to create YoutubeManager bean", e);
      throw new ConfigurationException("Unable to create YoutubeManager bean", e);
    }
  }

  protected Video getRandomYoutubeVideo() {

    String videoId = "";
    try {

      YouTube.Search.List searchListByKeywordRequest = youtube.search()
          .list("snippet");
      searchListByKeywordRequest.setMaxResults((long) 1)
          .setQ(randomWordGenerator.getRandomWord())
          .setType("video")
          .setKey(youtubeApiKey);
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
      videosListByIdRequest.setId(videoId).setKey(youtubeApiKey);
      VideoListResponse response = videosListByIdRequest.execute();
      video = response.getItems().get(0);
      LOGGER.info("VideoListResponse {}", response);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return video;
  }
}