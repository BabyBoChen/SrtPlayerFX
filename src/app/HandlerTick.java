package app;

import javafx.application.Platform;
import javafx.event.EventHandler;

public class HandlerTick implements EventHandler<AppEvent>{

    @Override
    public void handle(AppEvent AppEvent) {
        Platform.runLater(new Runnable(){
        
            @Override
            public void run() {
                AppEvent.controller.lblTimecode.setText(AppEvent.controller.clock.timecode());
            }
        });
    }       
}