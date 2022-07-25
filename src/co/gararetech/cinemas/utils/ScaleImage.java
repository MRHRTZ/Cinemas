package co.gararetech.cinemas.utils;

import javax.swing.ImageIcon;
import java.awt.Image;

public class ScaleImage {
    private ImageIcon icon;
    private int width;
    private int height;
    
    public ScaleImage(ImageIcon icon, int width, int height) {
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    
    public ImageIcon scaleImage() {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > this.getWidth())
        {
          nw = this.getWidth();
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > this.getHeight())
        {
          nh = this.getHeight();
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }
}
