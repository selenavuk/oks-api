package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GoogleSheetsServiceUtil {
    private static final String APPLICATION_NAME = "oks-rest-api";

    public static Sheets getSheets() throws IOException, GeneralSecurityException, ExecutionException, InterruptedException {
        CompletableFuture<Credential> credential = GoogleAuthorizeUtil.authorizeAsync();
        return new Sheets
                .Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential.get())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}