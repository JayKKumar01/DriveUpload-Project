package org.example;

import com.google.api.services.drive.Drive;

import java.io.File;

public class Main {
    public static void main(String[] args) {


        // checking ownership
        String fileId = "1vFJXJjHzSsb8CnGRHsrAA2j7AIEdFn0b";




        try {
            String credentialsPath = "C:\\Users\\jayte\\Downloads\\driveupload05-c01c30a3f788.json";

            // Initialize the Drive service
            Drive driveService = DriveServiceUtil.getDriveService(credentialsPath);




//            FileOwnerChecker.checkFileOwner(driveService,fileId);

            // Folder IDs to upload to
            String folderId1 = "11ZmcxTCKO2RMOzbUyDbhfWrz0oUqhHxd";

            // File to upload
            java.io.File file = new java.io.File("C:\\Users\\jayte\\Downloads\\IMG_6178.mov.mp4");

            // Upload the file with progress display
            DriveUploadWithProgress.uploadFileWithProgress(driveService, folderId1, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
