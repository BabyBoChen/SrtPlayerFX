package app;

import java.time.Duration;

import javafx.application.Platform;
import javafx.event.Event;

public class SrtPrompter implements Runnable {

    private Controller controller;

    SrtPrompter(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            /* while srtLoader is not ready, sleep for 0.5s */
            while (this.controller.srtLoader.ready == false) {
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                if (this.controller.clock.kill == true) {return;}
                /* System.out.println("log1"); */
            }
            /* Once srtLoader is ready, begin iterate through all the srtRecords */
            for (int i = 0; i < this.controller.srtLoader.records.size(); i++) {
                /* while paused, sleep for 0.5s */
                while (this.controller.clock.playing == false) {
                    try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                    if (this.controller.clock.kill == true) {return;}
                    /* System.out.println("log2"); */
                }
                /* if the playing reaches the end, stop playing and set timecode to 0 */
                if (this.controller.clock.timecode_ms >= this.controller.srtLoader.srtDuration){
                    Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));
                    this.controller.clock.lock.lock();
                    try{
                        this.controller.clock.playing = false;
                        this.controller.clock.timecode_ms = 0;
                        this.controller.clock.timecode_dur = Duration.ZERO;
                        this.controller.clock.refreshTimecode();
                    }finally{
                        this.controller.clock.lock.unlock();
                    }
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            controller.btnPlay.setText("Play");
                        }
                    });
                }
                try{
                    /* if the current timecode is greater than the current srtRecord's out,
                    jump to next srtRecord */
                    if (this.controller.clock.timecode_ms >= this.controller.srtLoader.records.get(i).out){
                        Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));
                    }
                    else{
                        if (i != 0){
                            /* if the current timecode is less than the previous srtRecord's out,
                            break the for loop, restart the loop from the first srtRecord */
                            if (this.controller.clock.timecode_ms < this.controller.srtLoader.records.get(i-1).out){
                                Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));
                                break;
                            }
                            /* while the current timecode is greater than the previous srtRecord's out
                            but hasn't reach the current srtRecord's in, wait until reaching the current srtRecord's in*/
                            if (this.controller.clock.timecode_ms > this.controller.srtLoader.records.get(i-1).out && 
                            this.controller.clock.timecode_ms < this.controller.srtLoader.records.get(i).in){
                                Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));
                                
                                while (this.controller.clock.timecode_ms > this.controller.srtLoader.records.get(i-1).out && 
                                this.controller.clock.timecode_ms < this.controller.srtLoader.records.get(i).in){
                                    try{Thread.sleep(50);}catch(InterruptedException e) {e.printStackTrace();}
                                    if (this.controller.clock.kill == true) {return;}
                                    /* System.out.println("log3"); */
                                }
                            }
                        }else{
                            /* same situation as above but dealing with i = 0 */
                            /* while the current timecode hasn't reached the current srtRecord's in
                            wait until reaching the current srtRecord's in*/
                            if (this.controller.clock.timecode_ms < this.controller.srtLoader.records.get(i).in){
                                Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));

                                while (this.controller.clock.timecode_ms < this.controller.srtLoader.records.get(i).in){
                                    Event.fireEvent(this.controller.textArea, new AppEvent("",this.controller, AppEvent.SHOW_SUB));
                                    try{Thread.sleep(50);}catch(InterruptedException e) {e.printStackTrace();}
                                    if (this.controller.clock.kill == true) {return;}
                                    /* System.out.println("log3.1"); */
                                }
                            }
                        }
                        /* if the current timecode is within the current srtRecord's span,
                        display the current srtRecord's sub and 
                        sleep until the timecode reaches this srtRecord's out*/
                        if (this.controller.clock.timecode_ms >= this.controller.srtLoader.records.get(i).in && 
                        this.controller.clock.timecode_ms <= this.controller.srtLoader.records.get(i).out){
                            Event.fireEvent(this.controller.textArea, new AppEvent(this.controller.srtLoader.records.get(i).sub,
                                            this.controller, AppEvent.SHOW_SUB));
                            
                            while (this.controller.clock.timecode_ms >= this.controller.srtLoader.records.get(i).in && 
                            this.controller.clock.timecode_ms <= this.controller.srtLoader.records.get(i).out){
                                try{Thread.sleep(50);}catch(InterruptedException e) {e.printStackTrace();}
                                if (this.controller.clock.kill == true) {return;}
                                /* System.out.println("log4"); */
                            }
                        }
                    }
                }catch(IndexOutOfBoundsException e){
                    break;
                }
            }
        }
    }

}