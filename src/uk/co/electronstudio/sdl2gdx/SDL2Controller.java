package uk.co.electronstudio.sdl2gdx;


import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.studiohartman.jamepad.ControllerAxis;
import com.studiohartman.jamepad.ControllerButton;
import org.libsdl.SDL;
import org.libsdl.SDL_Error;
import org.libsdl.SDL_GameController;
import org.libsdl.SDL_Joystick;

import static org.libsdl.SDL.*;

// TODO implement native SDL events.  Tried but they don't seem to work reliably on MacOS!

public class SDL2Controller implements RumbleController {
    private static final IntMap<ControllerButton> CODE_TO_BUTTON = new IntMap(ControllerButton.values().length);
    private static final IntMap<ControllerAxis> CODE_TO_AXIS = new IntMap(ControllerAxis.values().length);
    final SDL2ControllerManager manager;
    final Array<ControllerListener> listeners = new Array<>();
    final int device_index;
    public final SDL_Joystick joystick;
    final SDL_GameController controller;
    final float[] axisState;
    final boolean[] buttonState;
    final static Vector3 zero = new Vector3(0, 0, 0);


    public SDL2Controller(SDL2ControllerManager manager, int device_index) throws SDL_Error {
        this.manager = manager;
        this.device_index = device_index;

        joystick = SDL_Joystick.JoystickOpen(device_index);


        if (SDL.SDL_IsGameController(device_index)) {
            controller = SDL_GameController.GameControllerOpen(device_index);
            buttonState = new boolean[SDL_CONTROLLER_BUTTON_MAX];
            axisState = new float[SDL_CONTROLLER_AXIS_MAX];
        } else {
            controller = null;
            buttonState = new boolean[joystick.numButtons()];
            axisState = new float[joystick.numAxes()];
        }
        System.out.println("joystick " + joystick + " controller " + controller);
        if (joystick == null && controller == null) throw new SDL_Error();


    }

    public boolean isConnected() {
        return joystick.getAttached();
    }

    @Override
    public boolean canVibrate() {
        return false;
    }

    @Override
    public boolean isVibrating() {
        return false;
    }

    @Override
    public void startVibration(int duration, float strength) {

    }

    @Override
    public void cancelVibration() {

    }

    @Override
    public boolean supportsPlayerIndex() {
        return false;
    }
    //	public SDL2Controller(SDL2ControllerManager manager, SDL_Joystick joystick) {
//		this(manager, joystick, null);
//	}
//
//	public SDL2Controller(SDL2ControllerManager manager, SDL_Joystick joystick, SDL_GameController controller) {
//		this.manager = manager;
//		this.joystick = joystick;
//		this.controller = controller;
////		this.axisState = new float[GLFW.glfwGetJoystickAxes(index).limit()];
////		this.buttonState = new boolean[GLFW.glfwGetJoystickButtons(index).limit()];
////		this.hatState = new byte[GLFW.glfwGetJoystickHats(index).limit()];
////		this.name = GLFW.glfwGetJoystickName(index);
//	}

    void pollState() throws SDL_Error {
//		if(!GLFW.glfwJoystickPresent(index)) {
//			manager.disconnected(this);
//			return;
//		}
//
//		FloatBuffer axes = GLFW.glfwGetJoystickAxes(index);
//		if(axes == null) {
//			manager.disconnected(this);
//			return;
//		}
//		ByteBuffer buttons = GLFW.glfwGetJoystickButtons(index);
//		if(buttons == null) {
//			manager.disconnected(this);
//			return;
//		}
//		ByteBuffer hats = GLFW.glfwGetJoystickHats(index);
//		if(hats == null) {
//			manager.disconnected(this);
//			return;
//		}
//
//		for(int i = 0; i < axes.limit(); i++) {
//			if(axisState[i] != axes.get(i)) {
//				for(ControllerListener listener: listeners) {
//					listener.axisMoved(this, i, axes.get(i));
//				}
//				manager.axisChanged(this, i, axes.get(i));
//			}
//			axisState[i] = axes.get(i);
//		}


        for (int i = 0; i < axisState.length; i++) {
            if (axisState[i] != getAxis(i)) {
                for (ControllerListener listener : listeners) {
                    listener.axisMoved(this, i, getAxis(i));
                }
                manager.axisChanged(this, i, getAxis(i));
            }
            axisState[i] = getAxis(i);
        }


        for (int i = 0; i < buttonState.length; i++) {
            if (buttonState[i] != getButton(i)) {
                for (ControllerListener listener : listeners) {
                    if (getButton((i))) {
                        listener.buttonDown(this, i);
                    } else {
                        listener.buttonUp(this, i);
                    }
                }
                manager.buttonChanged(this, i, getButton(i));
            }
            buttonState[i] = getButton(i);
        }

    }

    @Override
    public void addListener(ControllerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ControllerListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public boolean getButton(int buttonCode) {
        if (controller != null) {
            return controller.getButton(buttonCode);
        } else {
            return joystick.getButton(buttonCode);
        }
    }

    @Override
    public float getAxis(int axisCode) {
        if (controller != null) {
            return controller.getAxis(axisCode);
        } else {
            return joystick.getAxis(axisCode);
        }
    }

    @Override
    public String getName() {
        if (controller != null) {
            return "SDL GameController " + controller.name();
        } else {
            return "SDL Joystick " + joystick.name();
        }
    }

    @Override
    public String getUniqueId() {
        if(controller != null) {
            return controller.name();
        } else {
            return joystick.GUID();
        }
    }

    @Override
    public int getMinButtonIndex() {
        return 0;
    }

    @Override
    public int getMaxButtonIndex() {
        return CODE_TO_BUTTON.size - 1;
    }

    @Override
    public int getAxisCount() {
        return CODE_TO_AXIS.size;
    }

    @Override
    public String toString() {
        return getName() + " instance:" + joystick.instanceID() + " " + " guid: " + joystick.GUID() + " v " + joystick.productVersion(device_index);
    }

    public void close() {
        joystick.close();
        if (controller != null) controller.close();
    }

    /**
     * Vibrate the controller using the new rumble API
     * This will return false if the controller doesn't support vibration or if SDL was unable to start
     * vibration (maybe the controller doesn't support left/right vibration, maybe it was unplugged in the
     * middle of trying, etc...)
     *
     * @param leftMagnitude  The speed for the left motor to vibrate (this should be between 0 and 1)
     * @param rightMagnitude The speed for the right motor to vibrate (this should be between 0 and 1)
     * @return Whether or not the controller was able to be vibrated (i.e. if rumble is supported)
     */
    public boolean rumble(float leftMagnitude, float rightMagnitude, int duration_ms) {
        return joystick.rumble(leftMagnitude, rightMagnitude, duration_ms);
    }

    public enum PowerLevel {
        UNKNOWN, EMPTY, LOW, MEDIUM, FULL, WIRED, MAX
    }

    public PowerLevel getPowerLevel() {
        switch (joystick.currentPowerLevel()) {
            case SDL.SDL_JOYSTICK_POWER_EMPTY:
                return PowerLevel.EMPTY;
            case SDL.SDL_JOYSTICK_POWER_LOW:
                return PowerLevel.LOW;
            case SDL.SDL_JOYSTICK_POWER_MEDIUM:
                return PowerLevel.MEDIUM;
            case SDL.SDL_JOYSTICK_POWER_FULL:
                return PowerLevel.FULL;
            case SDL.SDL_JOYSTICK_POWER_WIRED:
                return PowerLevel.WIRED;
            case SDL.SDL_JOYSTICK_POWER_MAX:
                return PowerLevel.MAX;
        }
        return PowerLevel.UNKNOWN;
    }

    public ControllerType getType() {
        if (controller != null) {
            switch (controller.getType()) {
                case SDL_CONTROLLER_TYPE_XBOX360:
                    return ControllerType.XBOX360;
                case SDL_CONTROLLER_TYPE_XBOXONE:
                    return ControllerType.XBOXONE;
                case SDL_CONTROLLER_TYPE_PS3:
                    return ControllerType.PS3;
                case SDL_CONTROLLER_TYPE_PS4:
                    return ControllerType.PS4;
                case SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_PRO:
                    return ControllerType.NINTENDO_SWITCH_PRO;
                case SDL_CONTROLLER_TYPE_VIRTUAL:
                    return ControllerType.VIRTUAL;
            }
        }
        return ControllerType.UNKNOWN;
    }

    public int getPlayerIndex() {
        if (controller != null) {
            return controller.getPlayerIndex();
        }
        return -1;
    }

    @Override
    public void setPlayerIndex(int index) {

    }

    @Override
    public ControllerMapping getMapping() {
        return null;
    }

    public enum ControllerType {
        UNKNOWN, XBOX360, XBOXONE, PS3, PS4, NINTENDO_SWITCH_PRO, VIRTUAL
    }

}
