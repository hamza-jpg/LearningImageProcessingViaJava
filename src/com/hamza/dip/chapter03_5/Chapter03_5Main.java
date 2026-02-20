package com.hamza.dip.chapter03_5;

import com.hamza.dip.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

public class Chapter03_5Main {
    
    public static void main(String[] args) {
        
        String filename = "images/test3.jpg";
        
        BufferedImage image = ImageUtils.readImage(filename);
        
        BufferedImage greyscale = toGrey(image);
        
        BufferedImage masked = maskMean(greyscale);
        
        if(image != null) {
            ImageUtils.showImage(image, "Original image");
            ImageUtils.showImage(greyscale, "Greyscale Version");
            ImageUtils.showImage(masked, "Masked(Mean) Version");
        }
        else {
            System.out.println("Error: Image has not found");
        }
    }
    
    public static BufferedImage toGrey(BufferedImage image) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage greyscale = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                
                Color color = new Color(image.getRGB(x, y));
                
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                
                int avg = (red+green+blue) / 3;
                
                Color newColor = new Color(avg, avg, avg);
                
                greyscale.setRGB(x, y, newColor.getRGB());
            }
        }
        
        return greyscale;
    }
    
    // Edges are ignored "Mean Filter"
    public static BufferedImage maskMean(BufferedImage image) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        /*
            We want to protect original pixel values for averaging process while applying the mask.
            Thus, we will write the mask applied version of the image to a new buffer
        */
        
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        
        // outer loop iterates through image
        for(int x = 1; x < width - 1; x++) {
            for(int y = 1; y < height - 1; y++) {
                
                int sum = 0;
                // Inner loop for the mask
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        Color color = new Color(image.getRGB(x+i, y+j));
                        sum += color.getRed();
                    }
                }
                
                int avg = sum / 9;
                Color newColor = new Color(avg, avg, avg);
                outputImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        return outputImage;
    }
    
    // Outside of the edges are 0 "Zero Padding"
    public static BufferedImage maskZero(BufferedImage image) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage outputImage = new BufferedImage(width, height ,BufferedImage.TYPE_INT_RGB);
        
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                
                int sum = 0;
                
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        
                        if(x+i < 0 || x+i >= width || y+j < 0 || y+j >= height) {
                            sum += 0;
                        }
                        else {
                            Color color = new Color(image.getRGB(x+i, y+j));
                            sum += color.getRed();
                        }
                    }
                }
                
                int avg = sum / 9;
                Color newColor = new Color(avg, avg, avg);
                outputImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        return outputImage;
    }
    
    
}
