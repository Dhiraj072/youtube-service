package com.github.dhiraj072.yrandom.youtubeservice.video;

import com.google.api.services.youtube.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/youtube")
public class VideoController {

  private final YoutubeManager youtubeManager;

  @Autowired
  public VideoController(YoutubeManager youtubeManager) {

    this.youtubeManager = youtubeManager;
  }

  @GetMapping("/video/random")
  Video getRandomVideo() {

    return youtubeManager.getRandomYoutubeVideo();
  }
}
