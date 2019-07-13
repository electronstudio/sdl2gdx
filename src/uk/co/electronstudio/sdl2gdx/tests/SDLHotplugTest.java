package uk.co.electronstudio.sdl2gdx.tests;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import org.libsdl.SDL;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;
import com.badlogic.gdx.math.Vector3;
import org.libsdl.SDL_Error;

public class SDLHotplugTest {
    private static Controller recent;
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args){
        SDL.SDL_SetHint("SDL_XINPUT_ENABLED", "0");
        SDL2ControllerManager manager = new SDL2ControllerManager();

        manager.addListenerAndRunForConnectedControllers(new ControllerListener() {
            @Override
            public void connected(Controller controller) {
                System.out.println("*** CONNECTED "+controller);
                recent=controller;
            }

            @Override
            public void disconnected(Controller controller) {
                System.out.println("*** DISCONNECTED "+controller);
            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                System.out.println("BUTTON DOWN "+controller+" "+buttonCode);
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                System.out.println("BUTTON UP "+controller+" "+buttonCode);
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                System.out.println("AXIS MOVED "+controller+" "+axisCode+" "+value);
                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                System.out.println("POV MOVED +"+controller+" "+povCode+" "+value.toString());
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
                System.out.println("XSLIDER MOVED +"+controller+" "+sliderCode+" "+value);
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
                System.out.println("YSLIDER MOVED +"+controller+" "+sliderCode+" "+value);
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
                System.out.println("ACCELEROMETER MOVED +"+controller+" "+accelerometerCode+" "+value);
                return false;
            }
        });


        while (true){
            try {
                manager.pollState();
//                if(recent!=null){
//                    System.out.println(recent.getButton(0));
//                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (SDL_Error sdl_error) {
                sdl_error.printStackTrace();
            }
        }

    }
}
