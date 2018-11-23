package org.libsdl;

import java.util.Objects;

/**
 *  \file SDL_joystick.h
 *
 *  Include file for SDL joystick event handling
 *
 * The term "device_index" identifies currently plugged in joystick devices between 0 and SDL_NumJoysticks(), with the exact joystick
 *   behind a device_index changing as joysticks are plugged and unplugged.
 *
 * The term "instance_id" is the current instantiation of a joystick device in the system, if the joystick is removed and then re-inserted
 *   then it will get a new instance_id, instance_id's are monotonically increasing identifiers of a joystick plugged in.
 *
 * The term JoystickGUID is a stable 128-bit identifier for a joystick device that does not change over time, it identifies class of
 *   the device (a X360 wired controller for example). This identifier is platform dependent.
 *
 *
 *  In order to use these functions, SDL_Init() must have been called
 *  with the ::SDL_INIT_JOYSTICK flag.  This causes SDL to scan the system
 *  for joysticks, and load appropriate drivers.
 *
 *  If you would like to receive joystick updates while the application
 *  is in the background, you should set the following hint before calling
 *  SDL_Init(): SDL_HINT_JOYSTICK_ALLOW_BACKGROUND_EVENTS
 */

public final class SDL_Joystick {
    final long ptr;
    final float AXIS_MIN, AXIS_MAX;
    SDL_Joystick(long ptr){
        this.ptr=ptr;
        AXIS_MAX=SDL.SDL_JOYSTICK_AXIS_MAX();
        AXIS_MIN=SDL.SDL_JOYSTICK_AXIS_MIN();
    }

    public static boolean eventStateQuery() throws SDL_Error{
        return eventState(-1);
    }

    public static boolean eventStateIgnore() throws SDL_Error{
        return eventState(0);
    }

    public static boolean eventStateEnable() throws SDL_Error{
        return eventState(1);
    }

    private static boolean eventState(int i) throws SDL_Error{
        switch (SDL.SDL_JoystickEventState(i)) {
            case 1:
                return true;
            case 0:
                return false;
            default:
                throw new SDL_Error();
        }
    }

    public static SDL_Joystick joystickFromInstanceID(SDL_JoystickID joyid){
        return new SDL_Joystick(SDL.SDL_JoystickFromInstanceID(joyid.id));
    }

    /**
     *  Get the implementation dependent name of a joystick.
     *  This can be called before any joysticks are opened.
     *  If no name can be found, this function returns NULL.
     */
    public static String joystickNameForIndex(int device_index){
        return SDL.SDL_JoystickNameForIndex(device_index);
    }

    public static SDL_Joystick JoystickOpen(int i){
        return new SDL_Joystick(SDL.SDL_JoystickOpen(i));
    }

    public static void joystickUpdate(){
        SDL.SDL_JoystickUpdate();
    }

    /**
     *  Count the number of joysticks attached to the system right now
     */
    public static int numJoysticks() throws SDL_Error{
        int num = SDL.SDL_NumJoysticks();
        if(num>=0) return num; else throw new SDL_Error();
    }


    public boolean getAttached(){
        return (SDL.SDL_JoystickGetAttached(ptr));
    }

    public float getAxis(int axis){
        int i = SDL.SDL_JoystickGetAxis(ptr, axis);
        if(i<0) return i/-AXIS_MIN;
        return i/AXIS_MAX;
    }

// TODO
//        public Vec getBall(int ball){
//            return (SDL.SDL_JoystickGetBall(ptr, button));
//        }

    public boolean getButton(int button){
        return (SDL.SDL_JoystickGetButton(ptr, button)==1);
    }


      /* TODO
    public static SDL_JoystickPowerLevel SDL_JoystickCurrentPowerLevel(SDL_Joystick joystick){
        SDL.SDL_JoystickCurrentPowerLevel(joystick.ptr);
    }
    */


    // TODO
    //JoystickGetDeviceGUID()

    //TODO
    //JoystickGetGUIDFromString

    //TODO
    //getGUID

    //TODO
    //SDL_JoystickGetGUIDString


    public int getHat(int hat){
        return SDL.SDL_JoystickGetHat(ptr, hat);
    }

    public SDL_JoystickID instanceID(){
        return new SDL_JoystickID(SDL.SDL_JoystickInstanceID(ptr));
    }

    public String name(){
        return SDL.SDL_JoystickName(ptr);
    }

    public int numAxes() throws SDL_Error{
        int num = SDL.SDL_JoystickNumAxes(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    public int numButtons() throws SDL_Error{
        int num = SDL.SDL_JoystickNumButtons(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    public int numBalls() throws SDL_Error{
        int num = SDL.SDL_JoystickNumBalls(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    public int numHats() throws SDL_Error{
        int num = SDL.SDL_JoystickNumHats(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    public void close(){
        SDL.SDL_JoystickClose(ptr);
    }



    /**
     *  Get the player index of a joystick, or -1 if it's not available
     *  This can be called before any joysticks are opened.
     */
   //public static int joystickGetDevicePlayerIndex(int device_index){
      // TODO return SDL.SDL_JoystickGetDevicePlayerIndex(device_index);
  // }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDL_Joystick that = (SDL_Joystick) o;
        return ptr == that.ptr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ptr);
    }
}
