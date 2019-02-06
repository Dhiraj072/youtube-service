package com.github.dhiraj072.yrandom.youtubeservice.exceptions;

/**
 * Something is not right in our configuration
 */
public class ConfigurationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ConfigurationException(String msg) {

    super(msg);
  }

  public ConfigurationException(String msg, Throwable cause) {

    super(msg, cause);
  }
}
