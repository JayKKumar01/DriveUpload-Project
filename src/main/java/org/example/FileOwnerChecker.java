package org.example;


import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;

public class FileOwnerChecker {

    public static void checkFileOwner(Drive driveService, String fileId) {
        try {
            Drive.Permissions.List permissionsRequest = driveService.permissions().list(fileId);
            permissionsRequest.setFields("permissions(id, emailAddress, role)");

            for (Permission permission : permissionsRequest.execute().getPermissions()) {
                if ("owner".equals(permission.getRole())) {
                    System.out.println("File Owner Email: " + permission.getEmailAddress());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to retrieve file permissions: " + e.getMessage());
        }
    }
}

