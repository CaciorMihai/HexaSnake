package game;

import gui.GUI;
import hexGrid.Hex;
import utils.Colours;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Run implements ActionListener {
    private Hex dummy;
    private Hex last;
    private GUI gui;
    
    private static Run ourInstance = new Run();
    public static Run getInstance() {
        return ourInstance;
    }
    private Run() {}
    
    public void setGui(GUI newGui) {
        gui = newGui;
        dummy = gui.getGrid().getCenter();
        gui.drawHex(dummy, Colours.Magenta, 0.43f);
        gui.updateFrame();
    }
    
    private void moveLeft() {
        if(dummy.getLeft() != null) {
            last = dummy;
            dummy = dummy.getLeft();
        }
    }
    private void moveTopL() {
        if(dummy.getTopL() != null) {
            last = dummy;
            dummy = dummy.getTopL();
        }
    }
    private void moveTopR() {
        if(dummy.getTopR() != null) {
            last = dummy;
            dummy = dummy.getTopR();
        }
    }
    private void moveRight() {
        if(dummy.getRight() != null) {
            last = dummy;
            dummy = dummy.getRight();
        }
    }
    private void moveBotL() {
        if(dummy.getBotL() != null) {
            last = dummy;
            dummy = dummy.getBotL();
        }
    }
    private void moveBotR() {
        if(dummy.getBotR() != null) {
            last = dummy;
            dummy = dummy.getBotR();
        }
    }
    
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        switch(cmd) {
            case "left":
                moveLeft();
                break;
            case "topL":
                moveTopL();
                break;
            case "topR":
                moveTopR();
                break;
            case "right":
                moveRight();
                break;
            case "botR":
                moveBotR();
                break;
            case "botL":
                moveBotL();
                break;
        }
        if(last != null) {
            gui.drawHex(last, Colours.Dark_Blue, 0.43f);
            gui.drawHex(last, Colours.Light_Blue, 0.37f);
        }
        gui.drawHex(dummy, Colours.Magenta, 0.43f);
        gui.updateFrame();
    }
}
