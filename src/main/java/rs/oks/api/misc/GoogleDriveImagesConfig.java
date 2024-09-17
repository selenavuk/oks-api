package rs.oks.api.misc;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Configuration
public class GoogleDriveImagesConfig {

    @Bean
    public GoogleDriveService googleDriveService() {
        return new GoogleDriveService();
    }

    public static class GoogleDriveService {

        // Gallery Images Folder ID: 1OK36QYvREEJbw6CNUw5BrnXdg_gEMqLA

        public String listFiles(String folderId) throws IOException {
/*
            String apiKey = "AIzaSyCk2ge7dZF27Tov8keREif8zfRqoozJXqk";
*/
            String apiKey = "AIzaSyCtDJKhCdkDl2UIL6f5ox05fEzYCLU6-rA";

            String query = URLEncoder.encode("'" + folderId + "' in parents and mimeType contains 'image/'", StandardCharsets.UTF_8);
            String url = "https://www.googleapis.com/drive/v3/files?q=" + query + "&fields=files(id,name,webViewLink)&key=" + apiKey;
            HttpGet request = new HttpGet(url);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        }
    }
}