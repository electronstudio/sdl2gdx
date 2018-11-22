package org.libsdl;

import com.badlogic.gdx.jnigen.JniGenSharedLibraryLoader;

final public class SDL {
    /*JNI

    #include "SDL.h"


    */

    static {
        new JniGenSharedLibraryLoader().load("jamepad");
    }

    // joystick.h

    public static native void SDL_JoystickClose(long ptr);/*
        SDL_JoystickClose((SDL_Joystick *) ptr);
    */

    // TODO
    //  public static native SDL_JoystickCurrentPowerLevel

    public static native int SDL_JoystickEventState(int state); /*
        return SDL_JoystickEventState(state);
    */

    public static native long SDL_JoystickFromInstanceID(int joyid); /*
        return (long)SDL_JoystickFromInstanceID(joyid);
    */

    public static native boolean SDL_JoystickGetAttached(long ptr);/*
        return (SDL_JoystickGetAttached((SDL_Joystick*) ptr)==SDL_TRUE);
    */

    public static native int SDL_JoystickGetAxis(long ptr, int axis); /*
        return SDL_JoystickGetAxis((SDL_Joystick*) ptr, axis);
    */

    // TODO
//    public static native int SDL_JoystickGetBall(long ptr, int ball); /*
//        int* dx;
//        int* dy;
//        SDL_JoystickGetBall((SDL_Joystick*) ptr, ball, dx, dy);
//        return SDL_JoystickGetAxis((SDL_Joystick*) ptr, axis);
//    */

    public static native int SDL_JoystickGetButton(long ptr, int button); /*
        return SDL_JoystickGetButton((SDL_Joystick*) ptr, button);
    */

    // TODO
    //SDL_JoystickGetDeviceGUID
    //TODO
    //SDL_JoystickGetGUID
    //TODO
    //SDL_JoystickGetGUIDFromString
    //TODO
    //SDL_JoystickGetGUIDString

    public static native int SDL_JoystickGetHat(long ptr, int hat); /*
        return SDL_JoystickGetHat((SDL_Joystick*) ptr, hat);
    */

    public static native int SDL_JoystickInstanceID(long ptr); /*
        return SDL_JoystickInstanceID((SDL_Joystick*) ptr);
    */

    public static native String SDL_JoystickName(long ptr); /*
        return  env->NewStringUTF(SDL_JoystickName((SDL_Joystick*) ptr));
    */

    public static native String SDL_JoystickNameForIndex(int i); /*
        return env->NewStringUTF(SDL_JoystickNameForIndex(i));
    */

    public static native int SDL_JoystickNumAxes(long ptr); /*
        return SDL_JoystickNumAxes((SDL_Joystick*) ptr);
    */

    public static native int SDL_JoystickNumBalls(long ptr); /*
        return SDL_JoystickNumBalls((SDL_Joystick*) ptr);
    */

    public static native int SDL_JoystickNumButtons(long ptr); /*
        return SDL_JoystickNumButtons((SDL_Joystick*) ptr);
    */


    public static native int SDL_JoystickNumHats(long ptr); /*
        return SDL_JoystickNumHats((SDL_Joystick*) ptr);
    */


    public static native long SDL_JoystickOpen(int i); /*
        return (long)SDL_JoystickOpen(i);
    */

    public static native void  SDL_JoystickUpdate();/*
         SDL_JoystickUpdate();
    */

    public static native int SDL_NumJoysticks(); /*
        return SDL_NumJoysticks();
    */


    ///// SDL.h

    public static native int SDL_Init(int flags); /*
        return SDL_Init(flags);
    */

    public static native void SDL_SetHints(); /*
       SDL_SetHint(SDL_HINT_JOYSTICK_ALLOW_BACKGROUND_EVENTS, "1");
       SDL_SetHint(SDL_HINT_ACCELEROMETER_AS_JOYSTICK, "0");
       SDL_SetHint(SDL_HINT_MAC_BACKGROUND_APP, "1");
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
