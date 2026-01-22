package com.hamza.dip.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ImageUtils {
    
    // Reads image from the disk and transforms it to BufferedImage Object
    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Displays image in a simple window
    public static void showImage(BufferedImage img, String title) {
        if(img == null) return;
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            
            JScrollPane scrollPane = new JScrollPane(label);
            
            frame.add(scrollPane);
            frame.setSize(1000, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
