package app;

import javafx.scene.Scene;  
import javafx.stage.Stage;  
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class App /* extends Application */ {

    static Controller controller;

    public static void main(String[] args) {
        /* launch(args); */
        AppFrame frame = new AppFrame("SrtPlayer");
        JFXPanel fxPanel = new JFXPanel();
        HandlerSceneClicked handlerSceneClicked = new HandlerSceneClicked(frame);
        HandlerSceneDragged handlerSceneDragged = new HandlerSceneDragged(handlerSceneClicked, frame);
        fxPanel.addMouseMotionListener(handlerSceneDragged);
        fxPanel.addMouseListener(new HandlerMouseEnterFrame(frame));
        frame.add(fxPanel);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("srtPlayer.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    controller = (Controller)loader.getController();
                    controller.frame = frame;
                    Scene mainScene = new Scene(root, 730, 115);
                    HandlerMouseEnterScene handlerMouseEnterScene = new HandlerMouseEnterScene(frame,controller,true);
                    HandlerMouseEnterScene handlerMouseExitScene = new HandlerMouseEnterScene(frame,controller,false);
                    mainScene.setFill(Color.TRANSPARENT);
                    fxPanel.addKeyListener(new HandlerKeyPressed(controller));
                    /* mainScene.setOnKeyPressed(e -> {HandlerKey.handlerKey(e, controller);}); */
                    mainScene.setOnMousePressed(handlerSceneClicked);
                    mainScene.setOnMouseReleased(handlerSceneClicked);
                    mainScene.setOnMouseEntered(handlerMouseEnterScene);
                    mainScene.setOnMouseExited(handlerMouseExitScene);
                    /* controller.textArea.setOnKeyPressed(e -> {HandlerKey.handlerKey(e, controller);});
                    controller.sldTime.setOnKeyPressed(e -> {HandlerKey.handlerKey(e, controller);}); */
                    fxPanel.setScene(mainScene);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });        
    }

	/* @Override */
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("srtPlayer.fxml"));
        Parent root = loader.load();
        controller = (Controller)loader.getController();
        Scene mainScene = new Scene(root, 720, 180);
        mainScene.setOnKeyPressed(e -> {HandlerKey.handlerKey(e, controller);});
        primaryStage.setTitle("SrtPlayer");
        primaryStage.setScene(mainScene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exit();
        });
        primaryStage.show();
    }

    private void exit(){
        Platform.exit();
        System.exit(0);
    }

}