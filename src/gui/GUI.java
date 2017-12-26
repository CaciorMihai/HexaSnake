package gui;

import game.Run;
import hexGrid.Grid;
import hexGrid.Hex;
import utils.Colours;
import utils.Coordinates;
import utils.DrawingAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class GUI {
    private JFrame imgFrame, buttonFrame;
    private BufferedImage image;
    private int distance;
    private int radius;
    private Grid grid;

    public GUI(int width, int height) {
        int buttonFrameWidth = 350;
        int buttonFrameHeight = 230;
        imgFrame = new JFrame("Image");
        buttonFrame = new JFrame("Buttons");
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        imgFrame.setContentPane(new JPanel(new BorderLayout()));
                        imgFrame.setSize(width ,height);
                        imgFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        imgFrame.setLocationRelativeTo(null);
                        imgFrame.setVisible(true);
                    }
                }
        );
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        distance = 70;
        radius = 6;
        grid = initGrid();
//        initButtons(buttonFrameWidth, buttonFrameHeight);
    }

    public Grid initGrid() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, Colours.BLACK);
            }
        }
        this.updateFrame();
        Grid grid = new Grid(radius);
//        for(Hex h : grid) {
//            drawHex(h, Colours.Dark_Blue, 0.43f);
//            drawHex(h, Colours.Light_Blue, 0.37f);
//        }
        Hex[][] map = grid.getMap();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                if(map[i][j] != null) {
                    drawHex(map[i][j], Colours.Dark_Blue, 0.43f);
                    drawHex(map[i][j], Colours.Light_Blue, 0.37f);
                }
            }
        }
        imgFrame.repaint();
        return grid;
    }
    private void initButtons(int width, int height) {
        JButton left, topL, topR, right, botR, botL;
        left = new JButton("Left");
        topL = new JButton("Top Left");
        topR = new JButton("Top Right");
        right = new JButton("Right");
        botR = new JButton("Bot Right");
        botL = new JButton("Bot Left");
        float l = 150f;
        float h = (float) Math.sqrt(3) * 0.5f;
        left.setBounds(30, 90, 90, 30);
        topL.setBounds(60, 30, 90, 30);
        topR.setBounds(180, 30, 90, 30);
        right.setBounds(210, 90, 90, 30);
        botL.setBounds(60, 150, 90, 30);
        botR.setBounds(180, 150, 90, 30);
        left.setActionCommand("left");
        topL.setActionCommand("topL");
        topR.setActionCommand("topR");
        right.setActionCommand("right");
        botL.setActionCommand("botL");
        botR.setActionCommand("botR");
        Run run = Run.getInstance();
        left.addActionListener(run);
        topL.addActionListener(run);
        topR.addActionListener(run);
        right.addActionListener(run);
        botL.addActionListener(run);
        botR.addActionListener(run);
        buttonFrame.add(left);
        buttonFrame.add(topL);
        buttonFrame.add(topR);
        buttonFrame.add(right);
        buttonFrame.add(botR);
        buttonFrame.add(botL);
        buttonFrame.setSize(width, height);
        buttonFrame.setLayout(null);
        buttonFrame.setVisible(true);
        buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void drawHex(Hex dummy, int colour, float sizeFactor) {
        DrawingAlgorithms alg = DrawingAlgorithms.getInstance();
        Coordinates P1, P2, P3, P4, P5, P6;
        float h, l;
        l = distance * sizeFactor;
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
        alg.drawLine(image, colour, P1, P2);
        alg.drawLine(image, colour, P2, P3);
        alg.drawLine(image, colour, P3, P4);
        alg.drawLine(image, colour, P4, P5);
        alg.drawLine(image, colour, P5, P6);
        alg.drawLine(image, colour, P6, P1);
        alg.fill(image, colour, colour, new Coordinates(Math.round(xc), Math.round(yc)));
    }
    
    public void updateFrame() {
        JLabel picLabel = new JLabel(new ImageIcon(image));
        imgFrame.add(picLabel);
        imgFrame.repaint();
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public Grid getGrid () {
        return grid;
    }
}
