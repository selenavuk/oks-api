package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GoogleAuthorizeUtil {

    private static CompletableFuture<Credential> authorizationFuture;

    public static CompletableFuture<Credential> authorizeAsync() {

        if(authorizationFuture != null) {
            return authorizationFuture;
        }

        authorizationFuture = CompletableFuture.supplyAsync(() -> {
            try {

                ClassPathResource resource = new ClassPathResource("google-spreadsheets-client-secret.json");
                InputStream inputStream = resource.getInputStream();

                GoogleClientSecrets clientSecrets = GoogleClientSecrets
                        .load(JacksonFactory.getDefaultInstance(), new InputStreamReader(inputStream));

                List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

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

                LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(59401).build();
                AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(flow, localServerReceiver);
                Credential credential = authorizationCodeInstalledApp.authorize("user");

//                URI authorizationUri = new URI(localServerReceiver.getRedirectUri());
//                Desktop.getDesktop().browse(authorizationUri);

                return credential;
            } catch (Exception e) {
                throw new RuntimeException("Failed to authorize", e);
            }
        });

        return authorizationFuture;
    }

}