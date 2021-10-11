package app;

import javax.swing.JFrame;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class HandlerMouseEnterScene implements EventHandler<MouseEvent>{

    private Controller controller;
    private boolean enter;   /* true means mouse entered scene, vice versa */
    public JFrame frame;

    HandlerMouseEnterScene(JFrame frame, Controller controller, boolean enter){
        this.frame = frame;
        this.controller = controller;
        this.enter = enter;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (this.enter == true){
            this.controller.btnOpen.setVisible(true);
            this.controller.btnOpen.setManaged(true);
            this.controller.lblTimecode.setVisible(true);
            this.controller.lblTimecode.setManaged(true);
            this.controller.btnPlay.setVisible(true);
            this.controller.btnPlay.setManaged(true);
            this.controller.btnExit.setVisible(true);
            this.controller.btnExit.setManaged(true);
        }else{
            this.controller.btnOpen.setVisible(false);
            this.controller.btnOpen.setManaged(false);
            this.controller.lblTimecode.setVisible(false);
            this.controller.lblTimecode.setManaged(false);
            this.controller.btnPlay.setVisible(false);
            this.controller.btnPlay.setManaged(false);
            this.controller.btnExit.setVisible(false);
            this.controller.btnExit.setManaged(false);
        }
    }

    
}