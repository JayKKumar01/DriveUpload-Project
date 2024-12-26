package org.example;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;

public class DriveUploadWithProgress {

    /**
     * Uploads a file to Google Drive and shows the progress.
     *
     * @param driveService Google Drive service instance
     * @param folderId     The folder ID to upload the file to
     * @param filePath     The local path of the file to be uploaded
     */
    public static void uploadFileWithProgress(Drive driveService, String folderId, java.io.File filePath) {
        try {
            // Metadata for the file to be uploaded
            File fileMetadata = new File();
            fileMetadata.setName(filePath.getName());
            fileMetadata.setParents(java.util.Collections.singletonList(folderId));

            // Content of the file
            AbstractInputStreamContent mediaContent = new FileContent("application/octet-stream", filePath);

            // Create and execute the Drive API file insert request with a progress listener
            Drive.Files.Create uploadRequest = driveService.files().create(fileMetadata, mediaContent);
            MediaHttpUploader uploader = uploadRequest.getMediaHttpUploader();

            // Enable Direct Upload (faster for large files)
            uploader.setDirectUploadEnabled(false);
            uploader.setChunkSize(2*1024*1024); // Minimum is 256KB


            // Add progress listener
            uploader.setProgressListener(new MediaHttpUploaderProgressListener() {
                @Override
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            StorageChecker.checkStorageUsage(driveService);
                            System.out.println("Upload Initiation Started...");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Upload Initiation Complete.");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.printf("Upload Progress: %.2f%%%n", uploader.getProgress() * 100);
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed Successfully!");
                            StorageChecker.checkStorageUsage(driveService);
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started.");
                            break;
                    }
                }
            });

            // Execute the upload request
            File uploadedFile = uploadRequest.execute();
            System.out.println("Uploaded File ID: " + uploadedFile.getId());
        } catch (IOException e) {
            System.err.println("An error occurred during the upload: " + e.getMessage());
        }
    }
}

