package app;

import javafx.application.Platform;
import javafx.event.EventHandler;

class HandlerSrtFail implements EventHandler<AppEvent>{

    @Override
    public void handle(AppEvent appEvent) {
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                appEvent.srtLoader.controller.textArea.setText("Unable to read this srt file\nPlease choose another one!");
                System.out.println("fail");
            }            
        });
    }    
}