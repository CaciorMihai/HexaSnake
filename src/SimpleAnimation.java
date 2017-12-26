import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class SimpleAnimation {
    private JFrame frame;
    private static SimpleAnimation animation;
    
    public static void main(String[] args){
        int width = 500;
        int height = 500;
        int i = 0;
        animation = new SimpleAnimation();
        animation.buildGUI();
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        animation.draw(image, width, height, i);
        while (true) {
            i++;
            animation.reDraw(image, 10, 10 + i, 5, 1693452815);
            JLabel picLabel = new JLabel(new ImageIcon(image));
            animation.frame.add(picLabel);
            animation.frame.repaint();      // JVM uses new co-ordinates in paintComponent() to repaint the ball
            try {
                Thread.sleep(20);
            } catch(InterruptedException e) {
            
            }
        }
    }
    
    public void reDraw(BufferedImage image, int x, int y, int size, int colour) {
        for (int i = x - size/2; i < x + size/2; i ++) {
            for (int j = x - size/2; j < y + size/2; j ++) {
                image.setRGB(i, j, colour);
            }
        }
    }
    
    public void draw(BufferedImage image, int x, int y, int c) {
        for (int i = 0; i < x; i ++) {
            for (int j = 0; j < y; j ++) {
                image.setRGB(i, j, 256*256*256*100 + c*256);
            }
        }
    }
    
    public void buildGUI() {
        frame = new JFrame("Simple animation");
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        frame.setContentPane(new DrawPanel(new BorderLayout()));
                        frame.setSize(500, 500);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                }
        );
    }
    class DrawPanel extends JPanel {
        public DrawPanel(LayoutManager layout){
            super(layout);
        }
        
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
//            for (int i = 0; i < 10; i++) {
//                for (int j = 0; j < 10; j++) {
                    g.fillRect(11, 11, 10, 10);
//                }
//            }
//            }
        }
    }
}