package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import rs.oks.api.controller.ImportController;
import rs.oks.api.misc.classes.GoogleClientSecretsConfig;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Component
public class GoogleAuthorizeUtil {

    private static AuthorizationCodeFlow flow;

    private static final Logger logger = Logger.getLogger(ImportController.class.getName());

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        Resource resource = new ClassPathResource("google-spreadsheets-client-secret.json");
        InputStream in = resource.getInputStream();


        GoogleClientSecrets clientSecrets = GoogleClientSecrets
                .load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);


        flow = new GoogleAuthorizationCodeFlow
                .Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientSecrets,
                scopes
        )
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .build();
    }

    private String tokensDirectoryPath = "tokens";
    private String clientSecretFile = "google-spreadsheets-client-secret.json";

//    local
//    private static String redirectUri = "http://localhost:8081/import/spreadsheets/callback";
//  production
    private static String redirectUri = "https://oks-api-production.up.railway.app/import/spreadsheets/callback";

    private List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

    public static String getAuthorizationUrl() throws IOException, GeneralSecurityException {
        return flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
    }

    public static Credential getCredentialFromCode(String code) throws IOException {
        TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        return flow.createAndStoreCredential(tokenResponse, "user");
    }
    private static CompletableFuture<Credential> authorizationFuture;

    public static CompletableFuture<Credential> authorizeAsync() {

        if(authorizationFuture != null) {
            logger.info("DEBUG: Returning existing authorization future." + authorizationFuture);
            return authorizationFuture;
        }

        authorizationFuture = CompletableFuture.supplyAsync(() -> {
            try {

                logger.info("DEBUG: Authorizing user.");

//                ClassPathResource resource = new ClassPathResource("google-spreadsheets-client-secret.json");

//                logger.info("DEBUG: Resource: " + resource.getURL());

//                logger.info("DEBUG: Resource: " + resource.getURI());
//                InputStream inputStream = resource.getInputStream();
//                if (inputStream == null) {
////                    throw new FileNotFoundException("Client secrets file not found in classpath.");
//                    logger.info("DEBUG: Client secrets file not found in classpath.");
//                } else {
//                    logger.info("DEBUG: Client secrets file found in classpath.");
//                }

                try {
//                    InputStream inputStream = resource.getInputStream();
//                    if (inputStream == null) {
//                        logger.info("DEBUG: Client secrets file not found in classpath.");
//                    } else {
  //                      logger.info("DEBUG: Client secrets file found in classpath.");
    //                }

//                    GoogleClientSecrets clientSecrets = GoogleClientSecrets
//                            .load(JacksonFactory.getDefaultInstance(), new InputStreamReader(inputStream));

                    GoogleClientSecrets clientSecrets = GoogleClientSecretsConfig.getClientSecrets();



                    logger.info("DEBUG: Client secrets: " + clientSecrets);
                    List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

                    logger.info("DEBUG: Scopes: " + scopes);

                    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                            .Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance(),
                            clientSecrets,
                            scopes
                    )
                            .setDataStoreFactory(new MemoryDataStoreFactory())
                            .setAccessType("offline")
                            .build();

                    logger.info("DEBUG: Flow: " + flow);

                    LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(59401).build();

                    logger.info("DEBUG: Local server receiver: " + localServerReceiver);
                    AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(flow, localServerReceiver);
                    Credential credential = authorizationCodeInstalledApp.authorize("user");
                    return credential;


                } catch (IOException e) {
                    logger.info("Error accessing client secrets file: " + e);
                }

//                GoogleClientSecrets clientSecrets = GoogleClientSecrets
//                        .load(JacksonFactory.getDefaultInstance(), new InputStreamReader(inputStream));

//                logger.info("DEBUG: Client secrets: " + clientSecrets);
//                List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);
//
//                logger.info("DEBUG: Scopes: " + scopes);
//
//                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
//                        .Builder(
//                            GoogleNetHttpTransport.newTrustedTransport(),
//                            JacksonFactory.getDefaultInstance(),
//                            clientSecrets,
//                            scopes
//                        )
//                        .setDataStoreFactory(new MemoryDataStoreFactory())
//                        .setAccessType("offline")
//                        .build();
//
//                logger.info("DEBUG: Flow: " + flow);
//
//                LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(59401).build();
//
//                logger.info("DEBUG: Local server receiver: " + localServerReceiver);
//                AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(flow, localServerReceiver);
//                Credential credential = authorizationCodeInstalledApp.authorize("user");

//                URI authorizationUri = new URI(localServerReceiver.getRedirectUri());
//                Desktop.getDesktop().browse(authorizationUri);

//                return credential;
            } catch (Exception e) {
                throw new RuntimeException("Failed to authorize", e);
            }

            return null;
        });

        return authorizationFuture;
    }

}