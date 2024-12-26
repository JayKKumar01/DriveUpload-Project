package org.example;

import com.google.api.services.drive.Drive;

public class Main {
    public static void main(String[] args) {

        try {
            // Path to the service account credentials file
            String credentialsPath = "C:\\Users\\jayte\\Downloads\\driveupload-445907-f591e0036951.json";

            // Initialize the Drive service
            Drive driveService = DriveServiceUtil.getDriveService(credentialsPath);

            // Create uploader instance
            DriveUploader uploader = new DriveUploader(driveService);

            // Folder IDs to upload to
            String folderId1 = "1yFPbbNBOI2f60slEsnHrLJ8p9VNxFgow";
            String folderId2 = "<FOLDER_ID_2>";

            // File to upload
            java.io.File file = new java.io.File("C:\\Users\\jayte\\Downloads\\IMG_5921.MOV");

            // Upload the file with progress display
            DriveUploadWithProgress.uploadFileWithProgress(driveService, folderId1, file);

            // Upload to multiple folders
//            uploader.uploadFile(folderId1, file);
//            uploader.uploadFile(folderId2, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
