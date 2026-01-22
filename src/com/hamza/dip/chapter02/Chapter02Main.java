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
        BufferedImage small = scaleImage(image, 0.5);
        BufferedImage big = scaleImage(image, 2.0);
        
        if(image != null) {
            System.out.println("Image has been loaded succesfully!");
            System.out.println("Width: " + image.getWidth() + " px");
            System.out.println("Height: " + image.getHeight() + " px");
            
            ImageUtils.showImage(image, "Original Image - Chapter 2");
            ImageUtils.showImage(small, "Small");
            ImageUtils.showImage(big, "big");
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
    
    // A method for playing with the resolution of the image
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
}
