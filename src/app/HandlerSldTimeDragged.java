package app;

import java.time.Duration;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class HandlerSldTimeDragged{
    
    public static void handlerSldTimeDragged(MouseEvent mouseEvent,Controller controller){
        controller.clock.lock.lock();
        try{
            long value = (long)controller.sldTime.getValue();
            controller.clock.timecode_ms = value;
            controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
            Event.fireEvent(controller.lblTimecode, new AppEvent(controller, AppEvent.TICK));
        }finally{
            controller.clock.lock.unlock();
        }
    }

}