package com.badlogic.gdx.controllers.sdl2.tests;

import com.badlogic.gdx.controllers.sdl2.SDL2ControllerManager;
import org.libsdl.SDL;
import org.libsdl.SDL_Error;

public class Test {
    public static void main(String[] args){

       // ControllerManager c = new ControllerManager();


//        System.out.println(SDL.SDL_GetError());

        SDL2ControllerManager manager = new SDL2ControllerManager();

        while (true){
            try {
                manager.pollState();
               System.out.println("number of conttrollers "+manager.getControllers().size);//get(1).getAxis(4));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (SDL_Error sdl_error) {
                sdl_error.printStackTrace();
            }
        }

    }
}
