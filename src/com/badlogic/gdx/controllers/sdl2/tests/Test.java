package com.badlogic.gdx.controllers.sdl2.tests;

import com.badlogic.gdx.controllers.sdl2.SDL2ControllerManager;
import org.libsdl.SDL;

public class Test {
    public static void main(String[] args){

       // ControllerManager c = new ControllerManager();


//        System.out.println(SDL.SDL_GetError());

        SDL2ControllerManager manager = new SDL2ControllerManager();

        while (true){
            manager.pollState();
        }

    }
}
