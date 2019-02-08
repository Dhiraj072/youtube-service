package com.github.dhiraj072.yrandom.youtubeservice.exceptions;

/**
 * This probably means something is incorrectly set up in our configuration
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
