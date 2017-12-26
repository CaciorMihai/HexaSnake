
import utils.Colours;

import java.awt.image.BufferedImage;
import java.lang.Math;

public class Draw {
    private static double r(float t) {
        return 0.08 * t * t;
    }
    
    public static void reDraw(BufferedImage image, int width, int height, int size, int colour, float t) {
        int x = (int) (r(t) * Math.cos(t)) + width/2;
        int y = (int) (r(t) * Math.sin(t)) + height/2;
        for (int i = x - size/2; i < x + size/2; i++) {
            for (int j = y - size/2; j < y + size/2; j++) {
                try {
                    image.setRGB(i, j, colour);
                } catch (ArrayIndexOutOfBoundsException e) {
                
                }
            }
        }
    }
    
    public static void draw(BufferedImage image, int x, int y) {
        for (int i = 0; i < x; i ++) {
            for (int j = 0; j < y; j ++) {
                image.setRGB(i, j, Colours.WHITE);
            }
        }
    }
    
    public static void drawAM(BufferedImage image, int width, int height, int size, int colour, float t) {
        int x = width / 2 + (int) t;
        float tt = t * 1 * 3.14f / height;
        int y = (int) ( Math.sin(20 * tt) * Math.sin(tt) * height/2 + height/2);
        for (int i = x - size/2; i < x + size/2; i++) {
            for (int j = y - size/2; j < y + size/2; j++) {
                try {
                    image.setRGB(i, j, colour);
                } catch (ArrayIndexOutOfBoundsException e) {
                
                }
            }
        }
        x = width / 2 - (int) t;
        y = (int) ( Math.sin(20 * tt) * Math.sin(tt) * height/2 + height/2);
        for (int i = x - size/2; i < x + size/2; i++) {
            for (int j = y - size/2; j < y + size/2; j++) {
                try {
                    image.setRGB(i, j, colour);
                } catch (ArrayIndexOutOfBoundsException e) {
                
                }
            }
        }
    }
}
