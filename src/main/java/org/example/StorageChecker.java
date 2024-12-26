package org.example;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;

import java.io.IOException;

public class StorageChecker {

    /**
     * Method to check storage used by the service account.
     *
     * @param driveService The Drive service object.
     */
    public static void checkStorageUsage(Drive driveService) {
        try {
            // Fetch the 'about' information
            About about = driveService.about().get()
                    .setFields("storageQuota")
                    .execute();

            // Get storage details
            About.StorageQuota storageQuota = about.getStorageQuota();

            long limit = storageQuota.getLimit(); // Total quota in bytes
            long usage = storageQuota.getUsage(); // Used space in bytes
            long usageInDrive = storageQuota.getUsageInDrive(); // Drive-specific usage in bytes

            // Convert to human-readable format
            System.out.println("Service Account Storage Details:");
            System.out.println("Total Storage Limit: " + bytesToHumanReadable(limit));
            System.out.println("Total Storage Used: " + bytesToHumanReadable(usage));
            System.out.println("Storage Left: " + bytesToHumanReadable(limit-usage));
        } catch (IOException e) {
            System.err.println("Error fetching storage details: " + e.getMessage());
        }
    }

    /**
     * Converts bytes to a human-readable format.
     *
     * @param bytes The size in bytes.
     * @return A human-readable string (e.g., GB, MB).
     */
    private static String bytesToHumanReadable(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
