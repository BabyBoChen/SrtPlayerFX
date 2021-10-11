package app;

import javafx.application.Platform;
import javafx.event.EventHandler;

class HandlerShowSub implements EventHandler<AppEvent>{

    @Override
    public void handle(AppEvent appEvent) {
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                appEvent.controller.textArea.setText(appEvent.sub);
            }            
        });
    }    
}