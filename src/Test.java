import hexGrid.Grid;
import hexGrid.Hex;
import utils.Colours;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import utils.Coordinates;
import utils.CoordinatesQueue;
import utils.DrawingAlgorithms;

public class Test {
    JFrame frame;
    JFrame buttonFrame;
    int width, height;
    BufferedImage image;
    DrawingAlgorithms alg = DrawingAlgorithms.getInstance();
    
    public Test() {
        width = 700;
        height = 700;
        frame = new JFrame("Nimic");
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        frame.setContentPane(new JPanel(new BorderLayout()));
                        frame.setSize(width + 200 ,height);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                }
        );
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    
    public void drawButtons() {
        JButton b1 = new JButton("Clink");
        b1.setBounds(25, 25, 80, 50);
        JButton b2 = new JButton("Clank");
        b2.setBounds(25, 75, 80, 50);
        buttonFrame = new JFrame("Nici aici");
        buttonFrame.add(b1);
        buttonFrame.add(b2);
        buttonFrame.setSize(120,190);
        buttonFrame.setLayout(null);
        buttonFrame.setVisible(true);
        buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawHexGrid() {
        Draw.draw(image, width, height);
        JLabel picLabel = new JLabel(new ImageIcon(image));
        frame.add(picLabel);
        frame.repaint();
        
        int radius = 9, distance = 40;
        Grid grid = new Grid(radius);
        for(Hex h : grid) {
            drawHex(h, distance, Colours.Light_Blue, Colours.Dark_Blue);
        }
        alg.blur(image);
        frame.repaint();
    }
    private void drawHex(Hex dummy, int distance, int innerCol, int outerCol) {
        Coordinates P1, P2, P3, P4, P5, P6;
        float h, l;
        l = distance * 0.45f;
        h = (float) Math.sqrt(3) * 0.5f * l;
        int x, y;
        float xc, yc;
        xc = image.getWidth() / 2 + dummy.getX() * distance/2;
        yc = image.getHeight() / 2 + dummy.getY()* distance/2;
        x = Math.round(xc + h);
        y = Math.round(yc + l * 0.5f);
        P1 = new Coordinates(x, y);
        x = Math.round(xc + h);
        y = Math.round(yc - l * 0.5f);
        P2 = new Coordinates(x, y);
        x = Math.round(xc);
        y = Math.round(yc - l * 1f);
        P3 = new Coordinates(x, y);
        x = Math.round(xc - h);
        y = Math.round(yc - l * 0.5f);
        P4 = new Coordinates(x, y);
        x = Math.round(xc - h);
        y = Math.round(yc + l * 0.5f);
        P5 = new Coordinates(x, y);
        x = Math.round(xc);
        y = Math.round(yc + l * 1f);
        P6 = new Coordinates(x, y);
        alg.drawLine(image, outerCol, P1, P2);
        alg.drawLine(image, outerCol, P2, P3);
        alg.drawLine(image, outerCol, P3, P4);
        alg.drawLine(image, outerCol, P4, P5);
        alg.drawLine(image, outerCol, P5, P6);
        alg.drawLine(image, outerCol, P6, P1);
        alg.fill(image, outerCol, outerCol, new Coordinates(Math.round(xc), Math.round(yc)));
        
        l = distance * 0.38f;
        h = (float) Math.sqrt(3) * 0.5f * l;
        x = Math.round(xc + h);
        y = Math.round(yc + l * 0.5f);
        P1 = new Coordinates(x, y);
        x = Math.round(xc + h);
        y = Math.round(yc - l * 0.5f);
        P2 = new Coordinates(x, y);
        x = Math.round(xc);
        y = Math.round(yc - l * 1f);
        P3 = new Coordinates(x, y);
        x = Math.round(xc - h);
        y = Math.round(yc - l * 0.5f);
        P4 = new Coordinates(x, y);
        x = Math.round(xc - h);
        y = Math.round(yc + l * 0.5f);
        P5 = new Coordinates(x, y);
        x = Math.round(xc);
        y = Math.round(yc + l * 1f);
        P6 = new Coordinates(x, y);
        alg.drawLine(image, innerCol, P1, P2);
        alg.drawLine(image, innerCol, P2, P3);
        alg.drawLine(image, innerCol, P3, P4);
        alg.drawLine(image, innerCol, P4, P5);
        alg.drawLine(image, innerCol, P5, P6);
        alg.drawLine(image, innerCol, P6, P1);
        alg.fill(image, innerCol, innerCol, new Coordinates(Math.round(xc), Math.round(yc)));
    }
    
    public void drawSpiral() {
        int size = 4;
        float t = 0;
        Draw.draw(image, width, height);
        while (true) {
            t += 0.05;
            Draw.reDraw(image, width, height, 6, Colours.GREY, t);
//            Draw.drawAM(image, width, height, size, Colours.GREY, t);
            if (t >= width) {
                t = 0;
                size += 2;
            }
            JLabel picLabel = new JLabel(new ImageIcon(image));
            frame.add(picLabel);
            frame.repaint();
            try {
                Thread.sleep(1);
            } catch(InterruptedException e) {

            }
        }
    }
}
