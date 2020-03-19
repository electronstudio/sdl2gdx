package org.libsdl;

import java.util.Objects;

/**
 *  \file SDL_joystick.h
 *<br>
 *  Include file for SDL joystick event handling
 *<br>
 * The term "device_index" identifies currently plugged in joystick devices between 0 and SDL_NumJoysticks(), with the exact joystick
 *   behind a device_index changing as joysticks are plugged and unplugged.
 *<br>
 * The term "instance_id" is the current instantiation of a joystick device in the system, if the joystick is removed and then re-inserted
 *   then it will get a new instance_id, instance_id's are monotonically increasing identifiers of a joystick plugged in.
 *<br>
 * The term JoystickGUID is a stable 128-bit identifier for a joystick device that does not change over time, it identifies class of
 *   the device (a X360 wired controller for example). This identifier is platform dependent.
 *<br>
 *<br>
 *  In order to use these functions, SDL_Init() must have been called
 *  with the ::SDL_INIT_JOYSTICK flag.  This causes SDL to scan the system
 *  for joysticks, and load appropriate drivers.
 *<br>
 *  If you would like to receive joystick updates while the application
 *  is in the background, you should set the following hint before calling
 *  SDL_Init(): SDL_HINT_JOYSTICK_ALLOW_BACKGROUND_EVENTS
 */

public final class SDL_Joystick {
    public long ptr;
    public final float AXIS_MIN, AXIS_MAX;

    /**
     * This is a unique ID for a joystick for the time it is connected to the system,
     * and is never reused for the lifetime of the application. If the joystick is
     * disconnected and reconnected, it will get a new ID.
     *
     * The ID value starts at 0 and increments from there. The value -1 is an invalid ID.
     */
    public final SDL_JoystickID instanceID;
    /**
     * The joystick structure used to identify an SDL joystick
     */
    SDL_Joystick(long ptr){
        this.ptr=ptr;
        this.instanceID = new SDL_JoystickID(SDL.SDL_JoystickInstanceID(ptr));
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

    /**
     *  Update the current state of the open joysticks.
     *
     *  This is called automatically by the event loop if any joystick
     *  events are enabled.
     */
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


    /**
     *  Returns SDL_TRUE if the joystick has been opened and currently connected, or SDL_FALSE if it has not.
     */
    public boolean getAttached(){
        return (ptr !=0 && SDL.SDL_JoystickGetAttached(ptr));
    }


    /**
     *  Get the current state of an axis control on a joystick.
     *
     *  The state is a value ranging from -1 to 1.
     *
     *  The axis indices start at index 0.
     */
    public float getAxis(int axis){
        if(!getAttached()) return 0;
        int i = SDL.SDL_JoystickGetAxis(ptr, axis);
        if(i<0) return i/-AXIS_MIN;
        return i/AXIS_MAX;
    }

    // ** TODO
    // *  Get the initial state of an axis control on a joystick.
    // *
    // *  The state is a value ranging from -32768 to 32767.
    // *
    // *  The axis indices start at index 0.
    // *
    // *  \return SDL_TRUE if this axis has any initial value, or SDL_FALSE if not.
    // */
    //extern DECLSPEC SDL_bool SDLCALL SDL_JoystickGetAxisInitialState(SDL_Joystick * joystick,
    //                                                   int axis, Sint16 *state);

// TODO
    /**
     *  Get the ball axis change since the last poll.
     *
     *  \return 0, or -1 if you passed it invalid parameters.
     *
     *  The ball indices start at index 0.
     */
//        public Vec getBall(int ball){
//            return (SDL.SDL_JoystickGetBall(ptr, button));
//        }

    /**
     *  Get the current state of a button on a joystick.
     *
     *  The button indices start at index 0.
     */
    public boolean getButton(int button){
        return (getAttached() && SDL.SDL_JoystickGetButton(ptr, button)==1);
    }



      /**
       *  Return the battery level of this joystick
       *
       *  SDL_JOYSTICK_POWER_UNKNOWN = -1,
       *  SDL_JOYSTICK_POWER_EMPTY = 0,
       *  SDL_JOYSTICK_POWER_LOW =1,
       * SDL_JOYSTICK_POWER_MEDIUM =2,
       * SDL_JOYSTICK_POWER_FULL =3,
       * SDL_JOYSTICK_POWER_WIRED =4,
       * SDL_JOYSTICK_POWER_MAX =5;
       */


    public int currentPowerLevel(){
         return (SDL.SDL_JoystickCurrentPowerLevel(ptr));
    }


    /**
     *  Get the current state of a POV hat on a joystick.
     *
     *  The hat indices start at index 0.
     *
     *  \return The return value is one of the following positions:
     *           - ::SDL_HAT_CENTERED
     *           - ::SDL_HAT_UP
     *           - ::SDL_HAT_RIGHT
     *           - ::SDL_HAT_DOWN
     *           - ::SDL_HAT_LEFT
     *           - ::SDL_HAT_RIGHTUP
     *           - ::SDL_HAT_RIGHTDOWN
     *           - ::SDL_HAT_LEFTUP
     *           - ::SDL_HAT_LEFTDOWN
     */
    public int getHat(int hat){
        if(!getAttached()) return 0;
        return SDL.SDL_JoystickGetHat(ptr, hat);
    }

    public SDL_JoystickID instanceID(){
        return instanceID;
    }

    public String name(){
        if(!getAttached()) return "unAttached";
        return SDL.SDL_JoystickName(ptr);
    }

    /**
     *  Get the number of general axis controls on a joystick.
     */
    public int numAxes() throws SDL_Error{
        if(!getAttached()) throw new SDL_Error();
        int num = SDL.SDL_JoystickNumAxes(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    /**
     *  Get the number of buttons on a joystick.
     */
    public int numButtons() throws SDL_Error{
        if(!getAttached()) throw new SDL_Error();
        int num = SDL.SDL_JoystickNumButtons(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    /**
     *  Get the number of trackballs on a joystick.
     *
     *  Joystick trackballs have only relative motion events associated
     *  with them and their state cannot be polled.
     */
    public int numBalls() throws SDL_Error{
        if(!getAttached()) throw new SDL_Error();
        int num = SDL.SDL_JoystickNumBalls(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    /**
     *  Get the number of POV hats on a joystick.
     */
    public int numHats() throws SDL_Error{
        if(!getAttached()) throw new SDL_Error();
        int num = SDL.SDL_JoystickNumHats(ptr);
        if(num>=0) return num; else throw new SDL_Error();
    }

    /**
     *  Close a joystick previously opened with SDL_JoystickOpen().
     */
    public void close(){
        if(getAttached()){
            SDL.SDL_JoystickClose(ptr);
            ptr=0;
        }
    }

    public String GUID() {
        if(!getAttached()) return "unAttached";
        return SDL.SDL_JoystickGUIDString(ptr);
    }

    public static int productVersion(int device_index) { return SDL.SDL_JoystickGetDeviceProductVersion(device_index);}


    /**
     *  Get the player index of a joystick, or -1 if it's not available
     *  This can be called before any joysticks are opened.
     */
   //public static int joystickGetDevicePlayerIndex(int device_index){
      // TODO return SDL.SDL_JoystickGetDevicePlayerIndex(device_index);
  // }


    /**
     *  Trigger a rumble effect
     *  Each call to this function cancels any previous rumble effect, and calling it with 0 intensity stops any rumbling.
     *
     *  \param joystick The joystick to vibrate
     *  \param low_frequency_rumble The intensity of the low frequency (left) rumble motor, from 0 to 1
     *  \param high_frequency_rumble The intensity of the high frequency (right) rumble motor, from 0 to 1
     *  \param duration_ms The duration of the rumble effect, in milliseconds
     *
     *  \return 0, or -1 if rumble isn't supported on this joystick
     */
    public boolean rumble(float leftMagnitude, float rightMagnitude, int duration_ms)  {

        if(!getAttached()) return false;

        //Check the values are appropriate
        boolean leftInRange = leftMagnitude >= 0 && leftMagnitude <= 1;
        boolean rightInRange = rightMagnitude >= 0 && rightMagnitude <= 1;
        if(!(leftInRange && rightInRange)) {
            throw new IllegalArgumentException("The passed values are not in the range 0 to 1!");
        }
        //65535
                // 32767
        return SDL.SDL_JoystickRumble(ptr, (int) (65535 * leftMagnitude), (int) (65535 * rightMagnitude), duration_ms);
    }





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
