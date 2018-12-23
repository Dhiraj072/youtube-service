package com.github.dhiraj072.yrandom.youtubeservice;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeAuthHelper {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(YoutubeAuthHelper.class);

  private final ConfigManager configManager;
  /**
   * Directory to store user credentials for this application.
   */
  private final java.io.File DATA_STORE_DIR = new java.io.File(
      System.getProperty("user.home"), ".credentials/java-youtube-api-tests");

  private FileDataStoreFactory DATA_STORE_FACTORY;

  private final JsonFactory JSON_FACTORY = JacksonFactory
      .getDefaultInstance();

  private HttpTransport HTTP_TRANSPORT;


  /**
   * Global instance of the scopes required by this quickstart.
   *
   * If modifying these scopes, delete your previously saved credentials at
   * ~/.credentials/drive-java-quickstart
   */
  private final Collection<String> SCOPES = Collections.singletonList(
      "https://www.googleapis.com/auth/youtube.force-ssl https://www.googleapis.com/auth/youtubepartner");

  @Autowired
  public YoutubeAuthHelper(ConfigManager configManager) {

    this.configManager = configManager;
  }

  /**
   * Build and return an authorized API client service, such as a YouTube Data
   * API client service.
   *
   * @return an authorized API client service
   * @param appName
   * @param clientSecret
   */
  private YouTube getYouTubeService(String appName,
      String clientSecret) throws IOException {

    Credential credential = authorize(clientSecret);
    return new YouTube.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(appName)
        .build();
  }

  /**
   * Creates an authorized Credential object.
   *
   * @return an authorized Credential object.
   * @param clientSecret
   */
  private Credential authorize(String clientSecret) throws IOException {

    // Load client secrets.
    InputStream in = new ByteArrayInputStream(clientSecret.getBytes());
    GoogleClientSecrets clientSecrets = GoogleClientSecrets
        .load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build();
    Credential credential = new AuthorizationCodeInstalledApp(
        flow, new LocalServerReceiver()).authorize("user");
    LOGGER.debug("Credentials saved to {}", DATA_STORE_DIR.getAbsolutePath());
    return credential;
  }

  public YouTube getAuthorizedYoutubeService()
      throws IOException, GeneralSecurityException {

    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
    return getYouTubeService(configManager.getAppName(), configManager.getClientSecret());
  }
}
