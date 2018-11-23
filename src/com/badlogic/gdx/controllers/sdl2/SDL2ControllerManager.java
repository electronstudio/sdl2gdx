package com.badlogic.gdx.controllers.sdl2;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerManager;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.utils.Array;
import org.libsdl.*;

import java.io.IOException;

public class SDL2ControllerManager implements ControllerManager {

	final Array<Controller> controllers = new Array<>();
	final Array<Controller> polledControllers = new Array<>();
	final Array<Controller> disconnectedControllers = new Array<>();
	//final Array<SDL_JoystickID> connectedInstanceIDs = new Array<>();
	final Array<ControllerListener> listeners = new Array<ControllerListener>();

	
	public SDL2ControllerManager() {
//		for(int i = GLFW.GLFW_JOYSTICK_1; i < GLFW.GLFW_JOYSTICK_LAST; i++) {
//			if(GLFW.glfwJoystickPresent(i)) {
//				controllers.add(new SDL2Controller(this, i));
//			}
//		}

		SDL.SDL_SetHints();


		if(SDL.SDL_Init( SDL.SDL_INIT_EVENTS | SDL.SDL_INIT_JOYSTICK | SDL.SDL_INIT_GAMECONTROLLER )!=0){
			throw new RuntimeException("SDL_Init failed: "+SDL.SDL_GetError());
		}


		try {
			SDL_GameController.addMappingsFromFile("gamecontrollerdb.txt");
		}catch (Exception e){
			System.out.println("Failed to load gamecontrollerdb.txt");
			e.printStackTrace();
		}

		try {
		for(int i = 0; i < SDL_Joystick.numJoysticks(); i++){
			String name = SDL_Joystick.joystickNameForIndex(i);
			System.out.printf("Joystick %d: %s\n", i, name!=null ? name : "Unknown Joystick");

				controllers.add(new SDL2Controller(this, i));

//                char guid[64];
//                SDL_assert(SDL_JoystickFromInstanceID(SDL_JoystickInstanceID(joystick)) == joystick);
//                SDL_JoystickGetGUIDString(SDL_JoystickGetGUID(joystick),
//                                          guid, sizeof (guid));
//                SDL_Log("       axes: %d\n", SDL_JoystickNumAxes(joystick));
//                SDL_Log("      balls: %d\n", SDL_JoystickNumBalls(joystick));
//                SDL_Log("       hats: %d\n", SDL_JoystickNumHats(joystick));
//                SDL_Log("    buttons: %d\n", SDL_JoystickNumButtons(joystick));
//                SDL_Log("instance id: %d\n", SDL_JoystickInstanceID(joystick));
//                SDL_Log("       guid: %s\n", guid);
//                SDL_Log("    VID/PID: 0x%.4x/0x%.4x\n", SDL_JoystickGetVendor(joystick), SDL_JoystickGetProduct(joystick));
//             }
		}
		}catch (SDL_Error e){
			e.printStackTrace();
		}

		if(Gdx.app!=null) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
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



	public void pollState() throws SDL_Error{
		SDL.SDL_PumpEvents();
	//	SDL.SDL_JoystickUpdate();
	//	sdl.update();

		for(int i = 0; i < SDL_Joystick.numJoysticks(); i++){
//			SDL_Joystick = SDL_Joystick.
//			SDL_JoystickID id =
		}

//		for(int i = GLFW.GLFW_JOYSTICK_1; i < GLFW.GLFW_JOYSTICK_LAST; i++) {
//			if(GLFW.glfwJoystickPresent(i)) {
//				boolean alreadyUsed = false;
//				for(int j = 0; j < controllers.size; j++) {
//					if(((SDL2Controller)controllers.get(j)).index == i) {
//						alreadyUsed = true;
//						break;
//					}
//				}
//				if(!alreadyUsed) {
//					SDL2Controller controller = new SDL2Controller(this, i);
//					connected(controller);
//				}
//			}
//		}
		
//		polledControllers.addAll(controllers);
//		for(Controller controller: polledControllers) {
//			((SDL2Controller)controller).pollState();
//		}
//		polledControllers.clear();
	}
	
	@Override
	public Array<Controller> getControllers () {
		return controllers;
	}

	@Override
	public void addListener (ControllerListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener (ControllerListener listener) {
		listeners.removeValue(listener, true);
	}

	@Override
	public void clearListeners () {
		listeners.clear();
	}
	
	void connected (SDL2Controller controller) {
		controllers.add(controller);
		for(ControllerListener listener: listeners) {
			listener.connected(controller);
		}
	}

	void disconnected (SDL2Controller controller) {
		controllers.removeValue(controller, true);
		for(ControllerListener listener: listeners) {
			listener.disconnected(controller);
		}
	}
	
	void axisChanged (SDL2Controller controller, int axisCode, float value) {
		for(ControllerListener listener: listeners) {
			listener.axisMoved(controller, axisCode, value);
		}
	}
	
	void buttonChanged (SDL2Controller controller, int buttonCode, boolean value) {
		for(ControllerListener listener: listeners) {
			if(value) {
				listener.buttonDown(controller, buttonCode);
			} else {
				listener.buttonUp(controller, buttonCode);
			}
		}
	}

	void hatChanged (SDL2Controller controller, int hatCode, PovDirection value) {
		for(ControllerListener listener: listeners) {
			listener.povMoved(controller, hatCode, value);
		}
	}

	@Override
	public Array<ControllerListener> getListeners () {
		return listeners;
	}
}
