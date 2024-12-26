package org.example;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class DriveServiceUtil {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static Drive getDriveService(String credentialsPath) throws GeneralSecurityException, IOException {
        var credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                .createScoped("https://www.googleapis.com/auth/drive");

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("DriveUploader").build();
    }
}

