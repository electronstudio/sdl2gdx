package uk.co.electronstudio.sdl2gdx.tests;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import org.libsdl.SDL;
import org.libsdl.SDL_Error;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

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
