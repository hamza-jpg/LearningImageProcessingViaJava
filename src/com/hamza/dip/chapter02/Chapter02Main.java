package com.hamza.dip.chapter02;

import com.hamza.dip.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Chapter02Main {
    
    public static void main(String[] args) {
        String filename = "images/test.jpg";
        
        System.out.println("Reading image: " + filename);
        
        BufferedImage image = ImageUtils.readImage(filename);
        toGrayScale(image);
        //toBinary(image, 127);
        //BufferedImage small = scaleImage(image, 0.5);
        BufferedImage big = scaleImage(image, 3.0);
        BufferedImage bigBilinear = scaleImageBilinear(image, 3.0);
        
        if(image != null) {
            System.out.println("Image has been loaded succesfully!");
            System.out.println("Width: " + image.getWidth() + " px");
            System.out.println("Height: " + image.getHeight() + " px");
            
            ImageUtils.showImage(image, "Original Image - Chapter 2");
            //ImageUtils.showImage(small, "Small");
            ImageUtils.showImage(big, "big with Nearest Neighbor method");
            ImageUtils.showImage(bigBilinear, "big with Bilinear Interpolation method");
        }
        
        else {
            System.out.println("Error: Image has not found!");
        }
        
        //Example: Finding the middle pixel value, seperating the colors 
        
        int x = image.getWidth() / 2;
        int y = image.getHeight() / 2;
        
        int pixelValue = image.getRGB(x, y);
        
        java.awt.Color color = new java.awt.Color(pixelValue);
        
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getRed();
        
        System.out.println("--- Pixel At Center ("+ x + ", " + y +") ---");
        System.out.println("Red: " + red);
        System.out.println("Green " + green);
        System.out.println("Blue " + blue);
    }
    
    // Transforming RGB image to greyscale
    public static void toGrayScale(BufferedImage img) {
        int x = img.getWidth();
        int y = img.getHeight();
        
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                
                int pixelVal = img.getRGB(i, j);               
                
                Color color = new Color(pixelVal);
                
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                
                int avg = (red + green + blue) / 3;
                
                Color newColor = new Color(avg, avg, avg);
                
                img.setRGB(i, j, newColor.getRGB());
            }
        }
    }
    
    // Thresholding for greyscale image
    public static void toBinary(BufferedImage img, int threshold) {
        int x = img.getWidth();
        int y = img.getHeight();
        
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                
                int pixelVal = img.getRGB(i, j);
                
                Color color = new Color(pixelVal);
                // Red=Green=Blue
                int intensity = color.getRed();
                
                Color newColor;
                
                if(intensity >= threshold) {
                    newColor = new Color(255, 255, 255);
                }
                
                else {
                    newColor = new Color(0, 0, 0);
                }
                
                img.setRGB(i, j, newColor.getRGB());
            }
        }
    }
    
    // A method for playing with the resolution of the image (Nearest Neighbor)
    public static BufferedImage scaleImage(BufferedImage img, double factor) {
         int width = img.getWidth();
         int height = img.getHeight();
         int newWidth = (int) (width * factor);
         int newHeight = (int) (height * factor);
         
         // New image for new scaled image
         BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
         
         // Iterating through pixels of new image
         for(int x = 0; x < newWidth; x++) {
             for(int y = 0; y < newHeight; y++) {
                 
                 // Finding the old coordinates
                 int srcX = (int) (x / factor);
                 int srcY = (int) (y / factor);
                 
                 // Limiting pixel overflow
                 srcX = Math.min(srcX,  width - 1);
                 srcY = Math.min(srcY, height - 1);
                 
                 // Copying the color of the original pixel to the new pixel.
                 int color = img.getRGB(srcX, srcY);
                 newImg.setRGB(x, y, color);
             }
         }
         
         return newImg;
    }
    
    // A method for playing with the resolution of the image (Bilinear Interpolation)
    // Just for a greyscale image thus it works with one random channel Red=Green=Blue
    public static BufferedImage scaleImageBilinear(BufferedImage img, double factor) {
        int width = img.getWidth();
        int height = img.getHeight();
        int newWidth = (int) (width * factor);
        int newHeight = (int) (height * factor);
        
        BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
        
        for(int x = 0; x < newWidth; x++) {
            for(int y = 0; y < newHeight; y++) {
                
                 // finding the old coordinates as double values
                 double srcX = x / factor;
                 double srcY = y / factor;
                 
                 // finding the left and top neighbors
                 int srcXi = (int) srcX;
                 int srcYi = (int) srcY;
                 
                 // finding the weights
                 double dx = srcX - srcXi;
                 double dy = srcY - srcYi;
                 
                 // determining 4 neighbors imagine a cross shape
                 int x1 = srcXi;
                 int y1 = srcYi;
                 int x2 = Math.min(srcXi + 1, width - 1); // to prevent overflow we use Math.min
                 int y2 = Math.min(srcYi + 1, height - 1);
                 
                 // taking color values of each neighbors
                 Color c11 = new Color(img.getRGB(x1, y1)); // Upper-left
                 Color c21 = new Color(img.getRGB(x2, y1)); // Upper-right
                 Color c12 = new Color(img.getRGB(x1, y2)); // Bottom-left
                 Color c22 = new Color(img.getRGB(x2, y2)); // Bottom-right
                 
                 double topRed = (c11.getRed()*(1-dx)) + (c21.getRed()*(dx));
                 double bottomRed = (c12.getRed()*(1-dx)) + (c22.getRed()*(dx));
                 double finalRed = (topRed*(1-dy)) + (bottomRed*(dy));
                 
                 Color newColor = new Color((int)finalRed, (int)finalRed, (int)finalRed);
                 newImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImg;
    }
}
