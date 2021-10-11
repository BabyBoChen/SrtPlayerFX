package app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HandlerMouseEnterFrame implements MouseListener {

    private AppFrame frame;
    public Controller controller;
    
    HandlerMouseEnterFrame(AppFrame frame){
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        this.frame.setSize(730, 115);
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        this.frame.setSize(610, 115);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

}