package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.core.io.ClassPathResource;
import rs.oks.api.controller.ImportController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.util.logging.Logger;

public class GoogleSheetsServiceUtil {
    private static final String APPLICATION_NAME = "oks-rest-api";
    private static final Logger logger = Logger.getLogger(ImportController.class.getName());

    public static Sheets getSheets() throws IOException, GeneralSecurityException, ExecutionException, InterruptedException {
        logger.info("DEBUG: Getting Google Sheets service.");
        CompletableFuture<Credential> credential = GoogleAuthorizeUtil.authorizeAsync();
        return new Sheets
                .Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential.get())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static CompletableFuture<String> getSignInUrl(String accessToken) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Load client secrets
                InputStream in = new ClassPathResource("google-spreadsheets-client-secret.json").getInputStream();
                GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                        JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

                // Build flow and trigger user authorization request
                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(), clientSecrets,
                        Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
                        .setDataStoreFactory(new MemoryDataStoreFactory())
                        .setAccessType("offline")
                        .build();

                Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);

                return flow.newAuthorizationUrl()
                        .setRedirectUri(clientSecrets.getDetails().getRedirectUris().get(0))
                        .setState(credential.getAccessToken())
                        .build();
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        });
    }

}