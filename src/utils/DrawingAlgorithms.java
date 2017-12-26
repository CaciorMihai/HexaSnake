package utils;

import java.awt.image.BufferedImage;

public class DrawingAlgorithms {
    private static DrawingAlgorithms ourInstance = new DrawingAlgorithms();
    
    public static DrawingAlgorithms getInstance () {
        return ourInstance;
    }
    
    private DrawingAlgorithms () {
    }
    
    public void drawPoints(final BufferedImage image, final int xc, final int yc,
                            final int x, final int y, final int colour) {
        if (xc - x >= 0) {
            if (yc + y < image.getHeight()) {
                image.setRGB(xc - x, yc + y, colour);
            }
            if (yc - y >= 0) {
                image.setRGB(xc - x, yc - y, colour);
            }
        }
        if (xc - y >= 0) {
            if (yc + x < image.getHeight()) {
                image.setRGB(xc - y, yc + x, colour);
            }
            if (yc - x >= 0) {
                image.setRGB(xc - y, yc - x, colour);
            }
        }
        if (xc + x < image.getWidth()) {
            if (yc + y < image.getHeight()) {
                image.setRGB(xc + x, yc + y, colour);
            }
            if (yc - y >= 0) {
                image.setRGB(xc + x, yc - y, colour);
            }
        }
        if (xc + y < image.getWidth()) {
            if (yc + x < image.getHeight()) {
                image.setRGB(xc + y, yc + x, colour);
            }
            if (yc - x >= 0) {
                image.setRGB(xc + y, yc - x, colour);
            }
        }
    }
    public void drawCircle(BufferedImage image, int xc, int yc, int radius, int colour) {
        int x = 0, y = radius;
        int d = 3 - 2 * radius;
        while (y >= x) {
            drawPoints(image, xc, yc, x, y, colour);
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            drawPoints(image, xc, yc, x, y, colour);
        }
    }
    public void fill(final BufferedImage image, final int colour,
                      final int edgeColour, final Coordinates center) {
        CoordinatesQueue queue = new CoordinatesQueue();
        Coordinates elem;
        queue.enQueue(new Coordinates(center.getX(), center.getY()));
        while (queue.size() > 0) {
            elem = queue.deQueue();
            if (elem.getX() < image.getWidth() - 1
                    && image.getRGB(elem.getX() + 1, elem.getY()) != edgeColour
                    && image.getRGB(elem.getX() + 1, elem.getY()) != colour) {
                queue.enQueue(new Coordinates(elem.getX() + 1, elem.getY()));
                image.setRGB(elem.getX() + 1, elem.getY(), colour);
            }
            if (elem.getY() < image.getHeight() - 1
                    && image.getRGB(elem.getX(), elem.getY() + 1) != edgeColour
                    && image.getRGB(elem.getX(), elem.getY() + 1) != colour) {
                queue.enQueue(new Coordinates(elem.getX(), elem.getY() + 1));
                image.setRGB(elem.getX(), elem.getY() + 1, colour);
            }
            if (elem.getX() > 0
                    && image.getRGB(elem.getX() - 1, elem.getY()) != edgeColour
                    && image.getRGB(elem.getX() - 1, elem.getY()) != colour) {
                queue.enQueue(new Coordinates(elem.getX() - 1, elem.getY()));
                image.setRGB(elem.getX() - 1, elem.getY(), colour);
            }
            if (elem.getY() > 0
                    && image.getRGB(elem.getX(), elem.getY() - 1) != edgeColour
                    && image.getRGB(elem.getX(), elem.getY() - 1) != colour) {
                queue.enQueue(new Coordinates(elem.getX(), elem.getY() - 1));
                image.setRGB(elem.getX(), elem.getY() - 1, colour);
            }
        }
    }
    
    public void drawLine(final BufferedImage image, final int colour,
                          final Coordinates start,
                          final Coordinates finish) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = finish.getX();
        int y2 = finish.getY();
        int x = x1;
        int y = y1;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int s1;
        if ((x2 - x1) >= 0) {
            s1 = 1;
        } else {
            s1 = -1;
        }
        int s2;
        if ((y2 - y1) >= 0) {
            s2 = 1;
        } else {
            s2 = -1;
        }
        boolean interchanged = false;
        if (dy > dx) {
            int aux = dy;
            dy = dx;
            dx = aux;
            interchanged = true;
        }
        int error = 2 * dy - dx;
        for (int i = 0; i < dx; i++) {
            if (x < image.getWidth() && y < image.getHeight()
                    && x > 0 && y > 0) {
                image.setRGB(x, y, colour);
            }
            while (error > 0) {
                if (interchanged) {
                    x += s1;
                } else {
                    y += s2;
                }
                error -= 2 * dx;
            }
            if (interchanged) {
                y += s2;
            } else {
                x += s1;
            }
            error += 2 * dy;
        }
    }
    public void blur(final BufferedImage image) {
//        float matrix[][] = {{1, 4, 6, 4, 1},
//                            {4, 16, 24, 16, 4},
//                            {6, 24, 36, 24, 6},
//                            {4, 16, 24, 16, 4},
//                            {1, 4, 6, 4, 1}};
        float matrix[][] = {{1, 2, 1},{2, 10, 2},{1, 2, 1}};
        float coef = 22f;
        short dim = 1;
        int r, g, b;
        for(int y = dim; y < image.getHeight() - dim; y++) {
            for(int x = dim; x < image.getWidth() - dim; x++) {
                r = 0;
                g = 0;
                b = 0;
                for(int i = -dim; i <= dim; i++) {
                    for(int j = -dim; j <= dim; j++) {
                        r += (this.getRed(image.getRGB(x + i, y + j))
                                * matrix[i + dim][j + dim] / coef);
                        g += (this.getGreen(image.getRGB(x + i, y + j))
                                * matrix[i + dim][j + dim] / coef);
                        b += (this.getBlue(image.getRGB(x + i, y + j))
                                * matrix[i + dim][j + dim] / coef);
                    }
                }
                image.setRGB(x, y, this.getARGB(r, g, b));
            }
        }
    }
    public int getRed(int argb) {
        return (argb >> 16) & (255);
    }
    public int getGreen(int argb) {
        return (argb >> 8) & (255);
    }
    public int getBlue(int argb) {
        return (argb & 255);
    }
    public int getARGB(int red, int green, int blue) {
        int alpha = 255;
        return (alpha << 24) + (red << 16) + (green << 8) + blue;
    }
}
