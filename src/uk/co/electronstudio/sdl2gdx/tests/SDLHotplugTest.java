package uk.co.electronstudio.sdl2gdx.tests;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;
import com.badlogic.gdx.math.Vector3;
import org.libsdl.SDL_Error;

public class SDLHotplugTest {
    private static Controller recent;
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args){
        SDL2ControllerManager manager = new SDL2ControllerManager();

        manager.addListenerAndRunForConnectedControllers(new ControllerListener() {
            @Override
            public void connected(Controller controller) {
                System.out.println("LISTENER CONNECTED "+controller);
                recent=controller;
            }

            @Override
            public void disconnected(Controller controller) {
                System.out.println("LISTENER DISCONNECTED "+controller);
            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                System.out.println("LISTENER BUTTON DOWN "+controller+" "+buttonCode);
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                System.out.println("LISTENER BUTTON UP "+controller+" "+buttonCode);
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                System.out.println("LISTENER AXIS MOVED "+controller+" "+axisCode+" "+value);
                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
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
