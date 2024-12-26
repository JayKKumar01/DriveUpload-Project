package org.example;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;

public class TransferOwnership {

    public static void transferFileOwnership(Drive driveService, String fileId, String newOwnerEmail) {
        try {
            // Create permission for the new owner
            Permission newOwnerPermission = new Permission();
            newOwnerPermission.setType("user");
            newOwnerPermission.setRole("owner");
            newOwnerPermission.setEmailAddress(newOwnerEmail);

            // Transfer ownership
            driveService.permissions().create(fileId, newOwnerPermission)
                    .setTransferOwnership(true)
                    .execute();

            System.out.println("Ownership transferred to: " + newOwnerEmail);
        } catch (IOException e) {
            System.err.println("Failed to transfer ownership: " + e.getMessage());
        }
    }
}

