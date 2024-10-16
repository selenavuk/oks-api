package rs.oks.api.misc.classes;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.io.StringReader;

public class GoogleClientSecretsConfig {

//    private static final String CLIENT_SECRETS_JSON_TEMPLATE = "{\n" +
//            "  \"web\": {\n" +
//            "    \"client_id\": \"72055840890-brmi6c7jn2fnj8830cqek5gjp4aomlj6.apps.googleusercontent.com\",\n" +
//            "    \"project_id\": \"oks-rest-api-435116\",\n" +
//            "    \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
//            "    \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
//            "    \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
//            "    \"client_secret\": \"%s\"\n" +
//            "  }\n" +
//            "}";

    private static final String CLIENT_SECRETS_JSON = "{\n" +
            "  \"web\": {\n" +
//            "    \"client_id\": \"72055840890-brmi6c7jn2fnj8830cqek5gjp4aomlj6.apps.googleusercontent.com\",\n" +
            "    \"client_id\": \"72055840890-1lq48c0k28crj2iht92b7s6rjb110kb6.apps.googleusercontent.com\",\n" +
            "    \"project_id\": \"oks-rest-api-435116\",\n" +
            "    \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "    \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "    \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
//            "    \"client_secret\": \"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\",\n" +
            "    \"client_secret\": \"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\",\n" +
            "    \"redirect_uris\": [\"http://localhost:59401/Callback\", \"https://oks-api-production.up.railway.app/Callback\"]\n" +
            "  }\n" +
            "}";

    public static GoogleClientSecrets getClientSecrets() throws IOException {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return GoogleClientSecrets.load(jsonFactory, new StringReader(CLIENT_SECRETS_JSON));
    }

//    public static GoogleClientSecrets getClientSecrets() throws IOException {
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new StringReader(CLIENT_SECRETS_JSON));
//
//
////        String vv = "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";
////        String clientSecretsJson = String.format(CLIENT_SECRETS_JSON_TEMPLATE, vv);
//        return GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new StringReader(clientSecretsJson));
//    }
}