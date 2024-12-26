package org.example;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.Collections;

public class DriveUploader {
    private final Drive driveService;

    public DriveUploader(Drive driveService) {
        this.driveService = driveService;
    }

    public void uploadFile(String folderId, java.io.File file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getName());
        fileMetadata.setParents(Collections.singletonList(folderId));

        FileContent fileContent = new FileContent("application/octet-stream", file);

        File uploadedFile = driveService.files().create(fileMetadata, fileContent)
                .setFields("id")
                .execute();

        System.out.println("File uploaded: " + uploadedFile.getId());
    }
}

