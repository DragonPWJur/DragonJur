package utils.runner;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

public final class GmailUtils {

    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final String APPLICATION_NAME = "Gmail project";

    private static final List<String> SCOPES = List.of(GmailScopes.MAIL_GOOGLE_COM);
    private static final String USER_ID = "me";
    private static final String EMAIL_END_PART = "@gmail.com";
    private static final String QUERY = "subject:You have been invited";

    public static Gmail getGmailService() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return service;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GmailUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecret =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecret, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get(TOKENS_DIRECTORY_PATH).toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }

    public static String extractPasswordFromEmail(Gmail service, int numericPart) throws IOException {
        String email = ProjectProperties.COMMON_EMAIL_PART + numericPart + EMAIL_END_PART;
        String combinedQuery = "to:" + email + " " + QUERY;

        ListMessagesResponse response = service.users().messages().list(USER_ID).setQ(combinedQuery).execute();
        if (response.getMessages() != null && !response.getMessages().isEmpty()) {
            String messageId = response.getMessages().get(0).getId();
            Message message = service.users().messages().get("me", messageId).execute();
            String body = new String(message.getPayload().getParts().get(0).getBody().decodeData());

            String[] lines = body.split("\n");
            String passwordValue = null;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("Password:")) {
                    passwordValue = lines[i + 1].trim().replace("&amp;", "&");
                }
            }

            return passwordValue;
        } else {

            return null;
        }
    }
}