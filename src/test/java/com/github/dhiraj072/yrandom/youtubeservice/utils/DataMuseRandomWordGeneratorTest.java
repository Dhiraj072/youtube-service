package com.github.dhiraj072.yrandom.youtubeservice.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataMuseRandomWordGeneratorTest {

  private RandomWordGenerator generator;

  @BeforeEach
  void setup() {

    generator = new DataMuseRandomWordGenerator();
  }

  @Test
  void testGetsRandomWordCorrectly() {

    assertNotNull(generator.getRandomWord());
  }
}