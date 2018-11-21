package org.libsdl;

import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;

final public class SDL {
    /*JNI

    #include "SDL.h"


    */

    public static class SDL_Joystick{
        long ptr;
        SDL_Joystick(long ptr){
            this.ptr=ptr;
        }
    }

    static {
        new JniGenSharedLibraryLoader().load("jamepad");
    }

    public static native int SDL_NumJoysticks(); /*
        return SDL_NumJoysticks();
    */

     public static native String SDL_JoystickNameForIndex(int i); /*
        return env->NewStringUTF(SDL_JoystickNameForIndex(i));
    */

    public static SDL_Joystick SDL_JoystickOpen(int i){
        return new SDL_Joystick(_SDL_JoystickOpen(i));
    }

    public static native long _SDL_JoystickOpen(int i); /*
        return (long)SDL_JoystickOpen(i);
    */


    public static native int SDL_Init(int flags); /*
        return SDL_Init(flags);
    */

    public static native void SDL_SetHints(); /*
       SDL_SetHint(SDL_HINT_JOYSTICK_ALLOW_BACKGROUND_EVENTS, "1");
       SDL_SetHint(SDL_HINT_ACCELEROMETER_AS_JOYSTICK, "0");
       SDL_SetHint(SDL_HINT_MAC_BACKGROUND_APP, "1");
    */

    public static native void  SDL_JoystickUpdate();/*
         SDL_JoystickUpdate();
    */
    public static native void SDL_GameControllerUpdate();/*
        SDL_GameControllerUpdate();
    */
    public static int SDL_INIT_TIMER = SDL_INIT_TIMER();
    private static native int SDL_INIT_TIMER(); /*
        return SDL_INIT_TIMER;
    */

    public static int SDL_INIT_AUDIO = SDL_INIT_AUDIO();
    private static native int SDL_INIT_AUDIO(); /*
        return SDL_INIT_AUDIO;
    */

    public static int SDL_INIT_VIDEO = SDL_INIT_VIDEO();
    private static native int SDL_INIT_VIDEO(); /*
        return SDL_INIT_VIDEO;
    */

    public static int SDL_INIT_JOYSTICK = SDL_INIT_JOYSTICK();
    private static native int SDL_INIT_JOYSTICK(); /*
        return SDL_INIT_JOYSTICK;
    */

    public static int SDL_INIT_HAPTIC = SDL_INIT_HAPTIC();
    private static native int SDL_INIT_HAPTIC(); /*
        return SDL_INIT_HAPTIC;
    */

    public static int SDL_INIT_GAMECONTROLLER = SDL_INIT_GAMECONTROLLER();
    private static native int SDL_INIT_GAMECONTROLLER(); /*
        return SDL_INIT_GAMECONTROLLER;
    */

    public static int SDL_INIT_EVENTS = SDL_INIT_EVENTS();
    private static native int SDL_INIT_EVENTS(); /*
        return SDL_INIT_EVENTS;
    */

    public static int SDL_INIT_SENSOR = SDL_INIT_SENSOR();
    private static native int SDL_INIT_SENSOR(); /*
        return SDL_INIT_SENSOR;
    */

    public static int SDL_INIT_NOPARACHUTE = SDL_INIT_NOPARACHUTE();
    private static native int SDL_INIT_NOPARACHUTE(); /*
        return SDL_INIT_NOPARACHUTE;
    */

    public static int SDL_INIT_EVERYTHING = SDL_INIT_EVERYTHING();
    private static native int SDL_INIT_EVERYTHING(); /*
        return SDL_INIT_EVERYTHING;
    */

    public static native String SDL_GetError(); /*
        return env->NewStringUTF(SDL_GetError());
    */

//    public static native int SDL_PollEvent(); /*
//        return SDL_PollEvent(SDL_Event* event)
//    */
//
//    public static native int SDL_PollEventResult(); /*
//        return event
//    */


}
