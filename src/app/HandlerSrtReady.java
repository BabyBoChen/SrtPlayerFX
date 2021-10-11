package app;

import javafx.application.Platform;
import javafx.event.EventHandler;

public class HandlerSrtReady implements EventHandler<AppEvent>{

    @Override
    public void handle(AppEvent appEvent) {
        Platform.runLater(new Runnable(){
        
            @Override
            public void run() {
                double max = (double)appEvent.srtLoader.srtDuration;
                appEvent.srtLoader.controller.sldTime.setValue(0);
                appEvent.srtLoader.controller.sldTime.setMax(max);
                appEvent.srtLoader.controller.btnPlay.setDisable(false);
                appEvent.srtLoader.controller.sldTime.addEventHandler(AppEvent.TICK, new HandlerSldTimeTick());
                appEvent.srtLoader.controller.textArea.setText("Ready!");
            }
        });
    }    
        
}