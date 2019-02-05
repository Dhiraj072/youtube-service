package com.github.dhiraj072.yrandom.youtubeservice.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TopicsTest {

  @Test
  void testGetsRandomTopic() {

    assertNotNull(Topics.getRandomTopic());
  }
}