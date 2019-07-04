package uk.co.electronstudio.sdl2gdx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerManager;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import org.libsdl.*;

public class SDL2ControllerManager implements ControllerManager {

    private final Array<Controller> controllers = new Array<>();
    private final Array<Controller> polledControllers = new Array<>();
    private final IntArray connectedInstanceIds = new IntArray(128);
    private final Array<ControllerListener> listeners = new Array<>();

    private boolean running = true;

    public SDL2ControllerManager() {
        SDL.SDL_SetHint("SDL_JOYSTICK_ALLOW_BACKGROUND_EVENTS", "1");
        SDL.SDL_SetHint("SDL_ACCELEROMETER_AS_JOYSTICK", "0");
        SDL.SDL_SetHint("SDL_MAC_BACKGROUND_APP", "1");


        if (SDL.SDL_Init(SDL.SDL_INIT_EVENTS | SDL.SDL_INIT_JOYSTICK | SDL.SDL_INIT_GAMECONTROLLER) != 0) {
            throw new RuntimeException("SDL_Init failed: " + SDL.SDL_GetError());
        }


        try {
            SDL_GameController.addMappingsFromFile("/gamecontrollerdb.txt", getClass());
        } catch (Exception e) {
            System.out.println("Failed to load gamecontrollerdb.txt");
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < SDL_Joystick.numJoysticks(); i++) {
                String name = SDL_Joystick.joystickNameForIndex(i);
                System.out.printf("Joystick %d: %s\n", i, name != null ? name : "Unknown Joystick");
                connected(new SDL2Controller(this, i));
            }
        } catch (SDL_Error e) {
            e.printStackTrace();
        }

        if (Gdx.app != null) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if(!running) return;
                    try {
                        pollState();
                    } catch (SDL_Error e) {
                        e.printStackTrace();
                    }
                    Gdx.app.postRunnable(this);
                }
            });
        }
    }


    public void pollState() throws SDL_Error {
        SDL.SDL_PumpEvents();
        //	SDL.SDL_JoystickUpdate();
        //	sdl.update();

        //	System.out.println("numJoysticks "+ SDL_Joystick.numJoysticks());
        //	System.out.println("connectedInstanceIds "+connectedInstanceIds);

        for (int i = 0; i < SDL_Joystick.numJoysticks(); i++) {
            int id = SDL.SDL_JoystickGetDeviceInstanceID(i);
            if (!connectedInstanceIds.contains(id)) {
//				System.out.println("connectedInstanceIds "+connectedInstanceIds+" does not contain "+id);
                connected(new SDL2Controller(this, i));
            }
        }


        polledControllers.addAll(controllers);
        for (Controller controller : polledControllers) {
            SDL2Controller c = (SDL2Controller) controller;
            if (c.isConnected()) {
                c.pollState();
            } else {
                disconnected(c);
            }
        }
        polledControllers.clear();

    }

    @Override
    public Array<Controller> getControllers() {
        return controllers;
    }

    @Override
    public void addListener(ControllerListener listener) {
        listeners.add(listener);
    }

    public void addListenerAndRunForConnectedControllers(ControllerListener listener){
        for(Controller controller : controllers){
            listener.connected(controller);
        }
        addListener(listener);
    }

    @Override
    public void removeListener(ControllerListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public void clearListeners() {
        listeners.clear();
    }

    private void connected(SDL2Controller controller) {
        System.out.println("connected " + controller);
        controllers.add(controller);
        connectedInstanceIds.add(controller.joystick.instanceID().id);
        for (ControllerListener listener : listeners) {
            listener.connected(controller);
        }
    }

    private void disconnected(SDL2Controller controller) {
        System.out.println("disconnected " + controller);
        controllers.removeValue(controller, false);
        connectedInstanceIds.removeValue(controller.joystick.instanceID().id);
        for (ControllerListener listener : listeners) {
            listener.disconnected(controller);
        }
        controller.close();
    }

    void axisChanged(SDL2Controller controller, int axisCode, float value) {
        for (ControllerListener listener : listeners) {
            listener.axisMoved(controller, axisCode, value);
        }
    }

    void buttonChanged(SDL2Controller controller, int buttonCode, boolean value) {
        for (ControllerListener listener : listeners) {
            if (value) {
                listener.buttonDown(controller, buttonCode);
            } else {
                listener.buttonUp(controller, buttonCode);
            }
        }
    }

    void hatChanged(SDL2Controller controller, int hatCode, PovDirection value) {
        for (ControllerListener listener : listeners) {
            listener.povMoved(controller, hatCode, value);
        }
    }

    @Override
    public Array<ControllerListener> getListeners() {
        return listeners;
    }

    public void close(){
        running=false;
        SDL.SDL_Quit();
    }
}
