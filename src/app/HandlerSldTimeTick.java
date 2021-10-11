package app;

import javafx.application.Platform;
import javafx.event.EventHandler;

public class HandlerSldTimeTick implements EventHandler<AppEvent>{

    @Override
    public void handle(AppEvent appEvent) {
        Platform.runLater(new Runnable(){
        
            @Override
            public void run() {
                double value = (double)appEvent.controller.clock.timecode_ms;
                appEvent.controller.sldTime.setValue(value);
            }
        });

    }
    
}