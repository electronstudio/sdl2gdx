package org.libsdl;

public class Joystick {

    public static class SDL_Joystick{
        long ptr;
        SDL_Joystick(long ptr){
            this.ptr=ptr;
        }


        public boolean getAttached(){
            return (SDL.SDL_JoystickGetAttached(ptr));
        }

        public float getAxis(int axis){
            int i = SDL.SDL_JoystickGetAxis(ptr, axis);
            if(i<0) return i/32768f; // FIXME must be a nicer way of doing this
            return i/32767;
        }

// TODO
//        public Vec getBall(int ball){
//            return (SDL.SDL_JoystickGetBall(ptr, button));
//        }

        public boolean getButton(int button){
            return (SDL.SDL_JoystickGetButton(ptr, button)==1);
        }


        //TODO
        //getGUID

        //TODO
        //SDL_JoystickGetGUIDString


        //TODO
        //getHat

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

    }

    public static class SDL_JoystickID{
        int id;
        SDL_JoystickID(int id) { this.id = id;}
    }


    static class SDL_Error extends RuntimeException{
        SDL_Error() {
            super(SDL.SDL_GetError());
        }
    }

    /* TODO
    public static SDL_JoystickPowerLevel SDL_JoystickCurrentPowerLevel(SDL_Joystick joystick){
        SDL.SDL_JoystickCurrentPowerLevel(joystick.ptr);
    }
    */

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


    // TODO
    //JoystickGetDeviceGUID()

    //TODO
    //JoystickGetGUIDFromString

    public static SDL_Joystick joystickFromInstanceID(SDL_JoystickID joyid){
        return new SDL_Joystick(SDL.SDL_JoystickFromInstanceID(joyid.id));
    }


    public static String joystickNameForIndex(int device_index){
        return SDL.SDL_JoystickNameForIndex(device_index);
    }



    public static SDL_Joystick JoystickOpen(int i){
        return new SDL_Joystick(SDL.SDL_JoystickOpen(i));
    }



    public static void joystickUpdate(){
        SDL.SDL_JoystickUpdate();
    }

    public static int numJoysticks() throws SDL_Error{
        int num = SDL.SDL_NumJoysticks();
        if(num>=0) return num; else throw new SDL_Error();
    }

}

