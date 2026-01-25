package com.hamza.dip.chapter03;

import com.hamza.dip.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Chapter03Main {
    public static void main(String[] args) {
        String filename = "images/test3.jpg";
        
        BufferedImage image = ImageUtils.readImage(filename);
        //toGrey(image);
        histogramEq(image);
        if(image != null) {            
            ImageUtils.showImage(image, "Original Image - Chapter 3");
        }
        else {
            System.out.println("Error: Image has not found!");
        }
    }
    
    public static void toGrey(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                
                int pixelVal = image.getRGB(i, j);
                
                Color color = new Color(pixelVal);
                
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                
                int avg = (red + green + blue) / 3;
                
                Color newColor = new Color(avg, avg, avg);
                
                image.setRGB(i, j, newColor.getRGB());
            }
        }
    }
    
    public static void histogramEq(BufferedImage img) {
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        int[] mapping = new int[256];
        
        int[] greylevels = countGreyLevels(img);
        
        double[] PDFs = getPDFs(greylevels, img);
        
        double[] CDFs = getCDFs(PDFs);
        
        for(int i = 0;  i < 256; i++) {
            
            int newColorVal = (int) Math.round(255 * CDFs[i]);
            
            Color newColor = new Color(newColorVal, newColorVal, newColorVal);
            
            mapping[i] = newColor.getGreen();
        }
        
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                
                int pixelVal = img.getRGB(i, j)  & 0xFF;
                
                int val = mapping[pixelVal];
                
                img.setRGB(i, j, new Color(val, val, val).getRGB());
            }
        }
        
                // --- DEBUG (HATA AYIKLAMA) BAŞLANGICI ---
        System.out.println("=== HATA AYIKLAMA RAPORU ===");

        // 1. Dizi Boyutları Kontrolü
        System.out.println("Gri Seviye Dizisi Boyutu: " + greylevels.length); // 256 olmalı
        System.out.println("PDF Dizisi Boyutu: " + PDFs.length);       // 256 olmalı

        // 2. CDF Kontrolü (Matematik doğru mu?)
        System.out.println("CDF[255] Değeri (1.0 olmalı): " + CDFs[255]);
        System.out.println("CDF[128] Değeri: " + CDFs[128]);

        // 3. Mapping (Haritalama) Kontrolü
        System.out.println("Eski 0 -> Yeni: " + mapping[0]);
        System.out.println("Eski 50 -> Yeni: " + mapping[50]);
        System.out.println("Eski 100 -> Yeni: " + mapping[100]);
        System.out.println("Eski 200 -> Yeni: " + mapping[200]);
        System.out.println("Eski 255 -> Yeni: " + mapping[255]);
        System.out.println("============================");
        // --- DEBUG SONU --
    }
    
    public static int[] countGreyLevels(BufferedImage img) {
        
        int[] greylevels = new int[256];
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        for(int i = 0;  i < w; i++) {
            for(int j = 0; j < h; j++) {
                
                int pixelVal = img.getRGB(i, j) & 0xFF;
                
                greylevels[pixelVal]++;
            }
        }
        
        return greylevels;
    }
    
    public static double[] getPDFs(int[] greylevels, BufferedImage img) {
        
        int w = img.getWidth();
        int h = img.getHeight();
        int pixels = w * h;
        
        double[] pdfs = new double[256];
        
        for(int i = 0; i < 256; i++) {
            pdfs[i] = (double)greylevels[i] / (double)pixels;
        }
        
        return pdfs;
    }
    
    public static double[] getCDFs(double[] PDFs) {
        
        double[] CDFs = new double[256];
        
        for(int i = 0; i < 256; i++) {
            for(int j = 0; j <= i; j++) {
                CDFs[i] += PDFs[j];
            }
        }
        
        return CDFs;
    }
}
