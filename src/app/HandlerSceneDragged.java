package app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class HandlerSceneDragged implements MouseMotionListener {

    private HandlerSceneClicked handlerSceneClicked;
    private JFrame frame;

    HandlerSceneDragged(HandlerSceneClicked handlerSceneClicked, JFrame frame){
        this.handlerSceneClicked = handlerSceneClicked;
        this.frame = frame;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (handlerSceneClicked.isDragged == true){
            int deltaX = mouseEvent.getXOnScreen() - handlerSceneClicked.x0;
            int deltaY = mouseEvent.getYOnScreen() - handlerSceneClicked.y0;
            this.frame.setBounds(handlerSceneClicked.currentX+deltaX,
            handlerSceneClicked.currentY+deltaY,this.frame.getWidth(),this.frame.getHeight());
        }

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

}