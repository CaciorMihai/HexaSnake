package game;

import gui.GUI;
import hexGrid.Hex;
import utils.Colours;
import utils.DrawingAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame extends JFrame implements KeyListener {
    private static SnakeGame ourInstance = new SnakeGame();
    public static SnakeGame getInstance() {
        return ourInstance;
    }
    private GUI gui;
    private short state;
    private ArrayList<Hex> snake;
    private Hex next;
    private Hex apple;
    private String name;
    private Boolean nameRead;
    private boolean gameRunning;
    private boolean hasEaten;
    private boolean hasMoved;
    private int time;
    private ArrayList<Player> archive;
    private final String Filename = "./scores.txt";
    
    private SnakeGame(){
        archive = new ArrayList<>();
        gui = new GUI(900, 800);
        nameRead = false;
        state = 0;
        snake = new ArrayList<>();
        
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        JTextField text = new JTextField("Please Enter Your Name", 20);
        text.setBounds(10, 10, 150, 20);
        text.setEnabled(false);
        add(text);
        
        TextArea area = new TextArea();
        area.setBounds(120,420,80, 20);
        area.addKeyListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(area);
        setSize(300,100);
        setLayout(null);
        setVisible(true);
        run();
    }
    public void run() {
        while(true) {
            name = "";
            nameRead = false;
            while (!nameRead) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
            
                }
            }
            gameRunning = true;
            game();
            archive.add(new Player(name, snake.size()));
            snake = new ArrayList<>();
            gui.initGrid();
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10) {
            nameRead = true;
        } else
        if (!nameRead) {
            if (e.getKeyChar() != '.') {
                name += e.getKeyChar();
            }
        } else {
            switch (e.getKeyChar()) {
                case 'a':
                    updateDirection(false);
                    break;
                case 'd':
                    updateDirection(true);
                    break;
                case 's':
                    System.out.println("Game Stopped");
                    gameRunning = false;
                    break;
                case 'e':
                    printScores();
                    System.exit(0);
                    break;
                default:
                    break;
            }
            if(!hasMoved) {
                move();
                hasMoved = true;
            }
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    private void paintSnake(Hex dummy) {
        gui.drawHex(dummy, Colours.Brown, 0.43f);
        gui.drawHex(dummy, Colours.Magenta, 0.37f);
    }
    private void paintApple(Hex dummy) {
        gui.drawHex(dummy, Colours.Dark_Green, 0.43f);
        gui.drawHex(dummy, Colours.Green, 0.37f);
    }
    private void paintBlank(Hex dummy) {
        gui.drawHex(dummy, Colours.Dark_Blue, 0.43f);
        gui.drawHex(dummy, Colours.Light_Blue, 0.37f);
    }
    private void updateDirection(Boolean clockWise) {
        if(clockWise) {
            if(state < 5) {
                state++;
            } else {
                state = 0;
            }
        } else {
            if(state > 0) {
                state--;
            } else {
                state = 5;
            }
        }
    }
    private void moveLeft() {
        next = snake.get(0).getLeft();
    }
    private void moveTopL() {
        next = snake.get(0).getTopL();
    }
    private void moveTopR() {
        next = snake.get(0).getTopR();
    }
    private void moveRight() {
        next = snake.get(0).getRight();
    }
    private void moveBotL() {
        next = snake.get(0).getBotL();
    }
    private void moveBotR() {
        next = snake.get(0).getBotR();
    }
    private void updateSnake(Boolean hasEaten) {
        if (next == null) {
//            System.out.println("Game Over!");
            gameRunning = false;
        } else {
            if (!hasEaten) {
                Hex dummy = snake.remove(snake.size() - 1);
                paintBlank(dummy);
            }
            snake.add(0, next);
            paintSnake(next);
            gui.updateFrame();
        }
    }
    private void placeApple() {
        Hex[][] map = gui.getGrid().getMap();
        Random rand = new Random();
        int x = 0, y = 0;
        Boolean bool = true;
        while(bool) {
            x = rand.nextInt(map.length);
            y = rand.nextInt(map.length);
            if(map[x][y] != null) {
                bool = false;
            }
            for(Hex h : snake) {
                if(h == map[x][y]) {
                    bool = true;
                }
            }
        }
        apple = map[x][y];
        paintApple(apple);
    }
    private void checkTail() {
        for(int i = 1; i < snake.size(); i++) {
            if(snake.get(0) == snake.get(i)) {
//                System.out.println("Game Over!");
                gameRunning = false;
            }
        }
    }
    private void move() {
        /*
         * state == 0  move topR
         * state == 1  move right
         * state == 2  move botR
         * state == 3  move botL
         * state == 4  move left
         * state == 5  move topL
         */
        switch(state) {
            case 0:
                moveLeft();
                break;
            case 1:
                moveTopL();
                break;
            case 2:
                moveTopR();
                break;
            case 3:
                moveRight();
                break;
            case 4:
                moveBotR();
                break;
            case 5:
                moveBotL();
                break;
        }
        checkTail();
        hasEaten = false;
        if(snake.get(0) == apple) {
            hasEaten = true;
            placeApple();
            if(time > 350) {
                time -= 15;
            } else if(time > 250) {
                time -= 5;
            } else if(time > 200) {
                time -= 1;
            }
        }
        updateSnake(hasEaten);
    }
    private void game() {
        time = 500;
        Hex head = gui.getGrid().getCenter();
        snake.add(0, head);
        for (Hex h : snake) {
            paintSnake(h);
        }
        placeApple();
        gui.updateFrame();
        while(gameRunning) {
            if(!hasMoved) {
                move();
                hasMoved = true;
            }
            if(time < 400 && hasEaten) {
                DrawingAlgorithms.getInstance().blur(gui.getImage());
                try {
                    Thread.sleep(time / 3);
                } catch (InterruptedException e) {
        
                }
            } else
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) { }
            hasMoved = false;
        }
    }
    private void printScores() {
        int buffer = 15;
//        getScores();
//        storeScores();
        Collections.sort(archive);
        for(int i = 0; i < Math.min(archive.size(), 10); i++) {
            System.out.print((i + 1) + "| " + archive.get(i).name);
            for(int j = 0; j < buffer - archive.get(i).name.length(); j++) {
                System.out.print(".");
            }
            System.out.println(archive.get(i).score);
        }
    }
    private void storeScores() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = "";
        for(Player p : archive) {
            content += p.toString();
        }
        try {
            fw = new FileWriter(Filename);
            bw = new BufferedWriter(fw);
            bw.append(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        }
    }
    private void getScores() {
        String line = "", everything = "";
        try {
            FileReader fileReader = new FileReader(Filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        
            while((line = bufferedReader.readLine()) != null) {
                everything += line;
            }
            String[] info = everything.split(".");
            for(int i = 0; i < info.length; i += 2) {
                archive.add(new Player(info[i], Integer.parseInt(info[i + 1])));
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
        } catch(IOException ex) {
        }
    }
}

class Player implements Comparable<Player> {
    String name;
    int score;
    Player(String name, int score) {
        this.name = name;
        this.score = score - 1;
    }
    
    @Override
    public String toString () {
        return name + "." + score + ".";
    }
    
    @Override
    public int compareTo (Player o) {
        return Integer.compare(o.score, score);
    }
}
