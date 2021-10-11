package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class HandlerSceneClicked implements EventHandler<MouseEvent>{

    public boolean isDragged = false;
    public int x0 = 0;
    public int y0 = 0;
    public int currentX;
    public int currentY;
    public JFrame frame;

    HandlerSceneClicked(JFrame frame){
        this.frame = frame;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.isDragged = mouseEvent.isPrimaryButtonDown();
        if (this.isDragged==true){
            x0 = (int)mouseEvent.getScreenX();
            y0 = (int)mouseEvent.getScreenY();
        }
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                currentX = frame.getX();
                currentY = frame.getY();
            }
        });
    }

}