package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageComparator {

    public static void compareAndHighlightDifference(String imagePath1, String imagePath2, String outputPath) {
        try {
            // Read images
            BufferedImage image1 = ImageIO.read(new File(imagePath1));
            BufferedImage image2 = ImageIO.read(new File(imagePath2));

            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();

            // Determine the common dimensions
            int commonWidth = Math.min(width1, width2);
            int commonHeight = Math.min(height1, height2);

            // Warn if dimensions are not the same
            if (width1 != width2 || height1 != height2) {
                System.out.println("Warning: Image dimensions do not match! Comparing only the overlapping region.");
            }

            // Create an output image with dimensions of the first image
            BufferedImage diffImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);

            boolean hasDifference = false;

            // Compare pixels in the overlapping region
            for (int y = 0; y < commonHeight; y++) {
                for (int x = 0; x < commonWidth; x++) {
                    int rgb1 = image1.getRGB(x, y);
                    int rgb2 = image2.getRGB(x, y);

                    if (rgb1 == rgb2) {
                        // Copy the pixel as-is if there is no difference
                        diffImage.setRGB(x, y, rgb1);
                    } else {
                        // Highlight differences in red
                        hasDifference = true;
                        diffImage.setRGB(x, y, Color.RED.getRGB());
                    }
                }
            }

            // Fill the remaining area of diffImage if dimensions don't match
            if (width1 > commonWidth || height1 > commonHeight) {
                for (int y = 0; y < height1; y++) {
                    for (int x = 0; x < width1; x++) {
                        if (y >= commonHeight || x >= commonWidth) {
                            diffImage.setRGB(x, y, image1.getRGB(x, y));
                        }
                    }
                }
            }

            // Save diff image to the output path
            ImageIO.write(diffImage, "png", new File(outputPath));

            if (hasDifference) {
                System.out.println("Differences found! Diff image saved at: " + outputPath);
            } else {
                System.out.println("No differences found within the common region.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred while processing images: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String imagePath1 = "image1.png";
        String imagePath2 = "image2.png";
        String outputPath = "diffImage.png";

        compareAndHighlightDifference(imagePath1, imagePath2, outputPath);
    }
}

