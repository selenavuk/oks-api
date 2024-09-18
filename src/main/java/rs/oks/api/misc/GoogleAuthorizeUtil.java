package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import rs.oks.api.controller.ImportController;
import rs.oks.api.misc.classes.GoogleClientSecretsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.io.FileReader;

@Component
public class GoogleAuthorizeUtil {

    private static AuthorizationCodeFlow flow;

    private static final Logger logger = Logger.getLogger(ImportController.class.getName());
    private static CompletableFuture<String> authorizationCodeFuture = new CompletableFuture<>();

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
private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

    public static String getAuthorizationUrl() throws IOException, GeneralSecurityException {
        return flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
    }

    public static GoogleClientSecrets getClientSecrets() throws IOException {
        try (FileReader reader = new FileReader("google-spreadsheets-client-secret.json")) {
            return GoogleClientSecrets.load(JSON_FACTORY, reader);
        }
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
                try {
//                    GoogleClientSecrets clientSecrets = GoogleClientSecretsConfig.getClientSecrets();
                    GoogleClientSecrets clientSecrets = getClientSecrets();
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

                    String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
                    String code = waitForAuthorizationCode(); // Implement this method to get the code from the user
                    TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
                    Credential credential = flow.createAndStoreCredential(tokenResponse, "user");

                    return credential;


                } catch (IOException e) {
                    logger.info("Error accessing client secrets file: " + e);
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to authorize", e);
            }

            return null;
        });

        return authorizationFuture;
    }
    private static String waitForAuthorizationCode() {
        try {
            return authorizationCodeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to get authorization code", e);
        }
    }

    public static void setAuthorizationCode(String code) {
        authorizationCodeFuture.complete(code);
    }
}