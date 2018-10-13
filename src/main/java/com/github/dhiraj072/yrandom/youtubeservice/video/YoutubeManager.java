package com.github.dhiraj072.yrandom.youtubeservice.video;

import com.github.dhiraj072.yrandom.youtubeservice.YoutubeAuthHelper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
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

  @Autowired
  YoutubeManager(YoutubeAuthHelper youtubeAuthHelper)
      throws IOException, GeneralSecurityException {

    youtube = youtubeAuthHelper.getAuthorizedYoutubeService();
  }

  Video getRandomYoutubeVideo() {

    String videoId = "";
    try {

      YouTube.Search.List searchListByKeywordRequest = youtube.search()
          .list("snippet");
      searchListByKeywordRequest.setMaxResults((long) 1);
      searchListByKeywordRequest.setQ("surfing");
      searchListByKeywordRequest.setType("video");

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
      videosListByIdRequest.setId(videoId);
      VideoListResponse response = videosListByIdRequest.execute();
      video = response.getItems().get(0);
      LOGGER.info("VideoListResponse {}", response);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return video;
  }
}