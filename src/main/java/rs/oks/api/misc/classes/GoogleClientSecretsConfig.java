package rs.oks.api.misc.classes;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.io.StringReader;

public class GoogleClientSecretsConfig {

    private static final String CLIENT_SECRETS_JSON = "{\n" +
            "  \"web\": {\n" +
            "    \"client_id\": \"920005375280-ts3e5fpna3os15j3rqj2fddj8cv897m5.apps.googleusercontent.com\",\n" +
            "    \"project_id\": \"oks-rest-api\",\n" +
            "    \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "    \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "    \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "    \"client_secret\": \"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\"\n" +
            "  }\n" +
            "}";

    public static GoogleClientSecrets getClientSecrets() throws IOException {
        return GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new StringReader(CLIENT_SECRETS_JSON));
    }
}