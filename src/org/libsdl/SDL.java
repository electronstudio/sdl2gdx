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
        return (uintptr_t)SDL_JoystickFromInstanceID(joyid);
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
        return (uintptr_t)SDL_JoystickOpen(i);
    */

    public static native void SDL_JoystickUpdate();/*
         SDL_JoystickUpdate();
    */

    public static native int SDL_NumJoysticks(); /*
        return SDL_NumJoysticks();
    */

    public static native int SDL_JoystickGetDeviceInstanceID(int device); /*
        return SDL_JoystickGetDeviceInstanceID(device);
    */

    public static native int SDL_JoystickGetDeviceProductVersion(int device_id); /*
        return SDL_JoystickGetDeviceProductVersion(device_id);
     */

    public static native boolean SDL_JoystickRumble(long ptr, int leftMagnitude, int rightMagnitude, int duration_ms); /*
        return SDL_JoystickRumble((SDL_Joystick *)ptr, leftMagnitude, rightMagnitude,  duration_ms) == 0;
    */

    public static native String SDL_GameControllerGetStringForAxis(int axis); /*
        return  env->NewStringUTF(SDL_GameControllerGetStringForAxis((SDL_GameControllerAxis)axis));
    */

    public static native String SDL_GameControllerGetStringForButton(int button); /*
        return  env->NewStringUTF(SDL_GameControllerGetStringForButton((SDL_GameControllerButton)button));
    */


    // GameController.h

    public static final int SDL_CONTROLLER_BINDTYPE_NONE = 0,
            SDL_CONTROLLER_BINDTYPE_BUTTON = 1,
            SDL_CONTROLLER_BINDTYPE_AXIS = 2,
            SDL_CONTROLLER_BINDTYPE_HAT = 3;


    /**
     * The list of axes available from a controller
     * <p>
     * Thumbstick axis values range from SDL_JOYSTICK_AXIS_MIN to SDL_JOYSTICK_AXIS_MAX,
     * and are centered within ~8000 of zero, though advanced UI will allow users to set
     * or autodetect the dead zone, which varies between controllers.
     * <p>
     * Trigger axis values range from 0 to SDL_JOYSTICK_AXIS_MAX.
     */

    public static final int SDL_CONTROLLER_AXIS_INVALID = -1,
            SDL_CONTROLLER_AXIS_LEFTX = 0,
            SDL_CONTROLLER_AXIS_LEFTY = 1,
            SDL_CONTROLLER_AXIS_RIGHTX = 2,
            SDL_CONTROLLER_AXIS_RIGHTY = 3,
            SDL_CONTROLLER_AXIS_TRIGGERLEFT = 4,
            SDL_CONTROLLER_AXIS_TRIGGERRIGHT = 5,
            SDL_CONTROLLER_AXIS_MAX = 6;

    /**
     * The list of buttons available from a controller
     */

    public static final int SDL_CONTROLLER_BUTTON_INVALID = -1,
            SDL_CONTROLLER_BUTTON_A = 0,
            SDL_CONTROLLER_BUTTON_B = 1,
            SDL_CONTROLLER_BUTTON_X = 2,
            SDL_CONTROLLER_BUTTON_Y = 3,
            SDL_CONTROLLER_BUTTON_BACK = 4,
            SDL_CONTROLLER_BUTTON_GUIDE = 5,
            SDL_CONTROLLER_BUTTON_START = 6,
            SDL_CONTROLLER_BUTTON_LEFTSTICK = 7,
            SDL_CONTROLLER_BUTTON_RIGHTSTICK = 8,
            SDL_CONTROLLER_BUTTON_LEFTSHOULDER = 9,
            SDL_CONTROLLER_BUTTON_RIGHTSHOULDER = 10,
            SDL_CONTROLLER_BUTTON_DPAD_UP = 11,
            SDL_CONTROLLER_BUTTON_DPAD_DOWN = 12,
            SDL_CONTROLLER_BUTTON_DPAD_LEFT = 13,
            SDL_CONTROLLER_BUTTON_DPAD_RIGHT = 14,
            SDL_CONTROLLER_BUTTON_MAX = 15;


    public static final int SDL_HAT_CENTERED = 0x00,
            SDL_HAT_UP = 0x01,
            SDL_HAT_RIGHT = 0x02,
            SDL_HAT_RIGHTUP = 0x03,
            SDL_HAT_DOWN = 0x04,
            //    SDL_HAT_DOWNUP = 0x05, // impossible
            SDL_HAT_RIGHTDOWN = 0x06,
            // down right up 0x07 // impossible
            SDL_HAT_LEFT = 0x08,
            SDL_HAT_LEFTUP = 0x09,
            // left right 0x10 // impossible
            // left right up 0x11 // impossible
            SDL_HAT_LEFTDOWN = 0x12;

    public static native int SDL_GameControllerAddMapping(String mappingString);/*
        return SDL_GameControllerAddMapping(mappingString);
    */

    // TODO //SDL_GameControllerAddMappingsFromRW

    public static native int SDL_GameControllerAddMappingsFromFile(String path); /*

     printf("NATIVE METHOD: loading from  \"%s\"\n", path);
     int result = SDL_GameControllerAddMappingsFromFile(path);
     printf("             result %d  %s\n", result, SDL_GetError());
        return result;
    */

    public static native void SDL_GameControllerClose(long ptr);/*
        SDL_GameControllerClose((SDL_GameController *) ptr);
    */


    public static native int SDL_GameControllerEventState(int state); /*
        return SDL_GameControllerEventState(state);
    */

    public static native long SDL_GameControllerFromInstanceID(int joyid); /*
        return (uintptr_t)SDL_GameControllerFromInstanceID(joyid);
    */

    public static native boolean SDL_GameControllerGetAttached(long ptr);/*
        return (SDL_GameControllerGetAttached((SDL_GameController*) ptr)==SDL_TRUE);
    */

    public static native int SDL_GameControllerGetAxis(long ptr, int axis); /*
        return SDL_GameControllerGetAxis((SDL_GameController*) ptr, (SDL_GameControllerAxis) axis);
    */

    public static native String SDL_JoystickGUIDString(long ptr); /*
          char guid[64];
          SDL_JoystickGetGUIDString(SDL_JoystickGetGUID((SDL_Joystick*)ptr), guid, sizeof (guid));
          return env->NewStringUTF(guid);
    */


    // TODO
//SDL_GameControllerGetAxisFromString
//SDL_GameControllerGetBindForAxis
//SDL_GameControllerGetBindForButton

    public static native int SDL_GameControllerGetButton(long ptr, int button); /*
        return SDL_GameControllerGetButton((SDL_GameController*) ptr, (SDL_GameControllerButton)button);
    */

    // TODO
    //SDL_GameControllerGetButtonFromString
    public static native long SDL_GameControllerGetJoystick(long ptr);/*
        return (uintptr_t)SDL_GameControllerGetJoystick((SDL_GameController*)ptr);
    */
    //SDL_GameControllerGetStringForAxis
    //SDL_GameControllerGetStringForButton
    //SDL_GameControllerMapping
    //SDL_GameControllerMappingForGUID


    public static native String SDL_GameControllerName(long ptr); /*
        return  env->NewStringUTF(SDL_GameControllerName((SDL_GameController*) ptr));
    */

    public static native String SDL_GameControllerNameForIndex(int i); /*
        return env->NewStringUTF(SDL_GameControllerNameForIndex(i));
    */


    public static native long SDL_GameControllerOpen(int i); /*
        return (uintptr_t)SDL_GameControllerOpen(i);
    */

    public static native void SDL_GameControllerUpdate();/*
        SDL_GameControllerUpdate();
    */

    public static native boolean SDL_IsGameController(int joystick_index); /*
        return (SDL_IsGameController(joystick_index)==SDL_TRUE);
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


    public static native void SDL_PumpEvents(); /*
        SDL_PumpEvents();
    */

    public static native int SDL_JOYSTICK_AXIS_MIN(); /*
        return SDL_JOYSTICK_AXIS_MIN;
    */

    public static native int SDL_JOYSTICK_AXIS_MAX(); /*
        return SDL_JOYSTICK_AXIS_MAX;
    */




    /*

//    public static native int SDL_PollEvent(); /*
//        return SDL_PollEvent(SDL_Event* event)
//    */
//
//    public static native int SDL_PollEventResult(); /*
//        return event
//    */


}
