package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;

/* 
pause_key = 19
tab_key = 9

home_key = 36

right_key = 39
left_key = 37

up_key = 38
down_key = 40

pageup_key = 33
pagedown_key = 34

M_key = 77
N_key = 78

H_key = 72
 */

public class HandlerKeyPressed implements KeyListener {

    private Controller controller;

    HandlerKeyPressed(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch(keyCode){
            case 9:
            System.out.print(controller.clock.timecode() + ",");
            long ms = controller.clock.timecode_ms-(int)(controller.clock.timecode_ms/1000)*1000;
            String ms2 = "";
            if (ms < 100){
                ms2 += "0";
                if (ms < 10){
                    ms2 += "0";
                }
            }
            ms2+=Long.toString(ms);
            System.out.println(ms2);
            break;
            case 19:
            if (controller.srtLoader.ready==true){
                controller.play();
            }
            break;
            case 39:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 100;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 37:
            controller.clock.lock.lock();
            try{
                if(controller.clock.timecode_ms <= 100){
                    controller.clock.timecode_ms = 0;
                    controller.clock.timecode_dur = Duration.ZERO;
                    controller.clock.refreshTimecode();
                }else{
                    controller.clock.timecode_ms -= 100;
                    controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                    controller.clock.refreshTimecode();
                }
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 38:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 1000;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 40:
            controller.clock.lock.lock();
            try{
                if(controller.clock.timecode_ms <= 1000){
                    controller.clock.timecode_ms = 0;
                    controller.clock.timecode_dur = Duration.ZERO;
                    controller.clock.refreshTimecode();
                }else{
                    controller.clock.timecode_ms -= 1000;
                    controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                    controller.clock.refreshTimecode();
                }
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 33:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 5000;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 34:
            controller.clock.lock.lock();
            try{
                if(controller.clock.timecode_ms <= 5000){
                    controller.clock.timecode_ms = 0;
                    controller.clock.timecode_dur = Duration.ZERO;
                    controller.clock.refreshTimecode();
                }else{
                    controller.clock.timecode_ms -= 5000;
                    controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                    controller.clock.refreshTimecode();
                }
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 77:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 60000;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 78:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 600000;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 72:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms += 600000*6;
                controller.clock.timecode_dur = Duration.ZERO.plusMillis(controller.clock.timecode_ms);
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
            case 36:
            controller.clock.lock.lock();
            try{
                controller.clock.timecode_ms = 0;
                controller.clock.timecode_dur = Duration.ZERO;
                controller.clock.refreshTimecode();
            }finally{
                controller.clock.lock.unlock();
            }break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    

}