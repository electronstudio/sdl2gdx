package org.libsdl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public final class SDL_GameController {
    final long ptr;

    final float AXIS_MIN, AXIS_MAX;

    SDL_GameController(long ptr){
        this.ptr=ptr;
        AXIS_MAX=SDL.SDL_JOYSTICK_AXIS_MAX();
        AXIS_MIN=SDL.SDL_JOYSTICK_AXIS_MIN();
    }

    public void close(){
        SDL.SDL_GameControllerClose(ptr);
    }

    public boolean getAttached(){
        return (SDL.SDL_GameControllerGetAttached(ptr));
    }

    public float getAxis(int axis){
        int i = SDL.SDL_GameControllerGetAxis(ptr, axis);
        if(i<0) return i/-AXIS_MIN;
        return i/AXIS_MAX;
    }

// TODO
//getAxisFromString
    // //getBindForButton

    public boolean getButton(int button){
        return (SDL.SDL_GameControllerGetButton(ptr, button)==1);
    }


    //TODO

    //getButtonFromString





    public String name(){
        return SDL.SDL_GameControllerName(ptr);
    }

    public SDL_Joystick getJoystick(){
        return new SDL_Joystick(SDL.SDL_GameControllerGetJoystick(ptr));
    }

    //TODO
    //getStringForAxis
    //getStringForButton
    //getMapping


    /**
     * This method adds mappings held in the specified file. The file is copied to the temp folder so
     * that it can be read by the native code (if running from a .jar for instance)
     *
     * @param path The path to the file containing controller mappings.
     * @throws IOException if the file cannot be read, copied to a temp folder, or deleted.
     * @throws IllegalStateException if the mappings cannot be applied to SDL
     */
    public static boolean addMappingsFromFile(String path) throws IOException, IllegalStateException, SDL_Error {
        /*
        Copy the file to a temp folder. SDL can't read files held in .jars, and that's probably how
        most people would use this library.
         */
        Path extractedLoc = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir"), path);
        Files.copy(ClassLoader.getSystemResourceAsStream(path), extractedLoc,
                StandardCopyOption.REPLACE_EXISTING);

        System.out.println("EXTRACTED LOC "+extractedLoc.toString());

        int result = SDL.SDL_GameControllerAddMappingsFromFile(extractedLoc.toString());

        Files.delete(extractedLoc);

        return (intToBoolean(result));
    }


    public boolean addMapping(String mappingString) throws SDL_Error{
        return intToBoolean(SDL.SDL_GameControllerAddMapping(mappingString));
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

    private static boolean eventState(int i) throws SDL_Error {
        return intToBoolean(SDL.SDL_GameControllerEventState(i));
    }

    private static boolean intToBoolean(int i) throws SDL_Error{
        if(i>0) return true;
        else if(i==0) return false;
        else throw new SDL_Error();
    }


    // TODO
    //GameControllerGetDeviceGUID()

    //TODO
    //GameControllerGetGUIDFromString

    public static SDL_GameController GameControllerFromInstanceID(SDL_GameControllerID joyid){
        return new SDL_GameController(SDL.SDL_GameControllerFromInstanceID(joyid.id));
    }


    public static String GameControllerNameForIndex(int device_index){
        return SDL.SDL_GameControllerNameForIndex(device_index);
    }

    public static SDL_GameController GameControllerOpen(int i){
        return new SDL_GameController(SDL.SDL_GameControllerOpen(i));
    }

    public static void GameControllerUpdate(){
        SDL.SDL_GameControllerUpdate();
    }

    public boolean isGameController(int joystick_index){
        return SDL.SDL_IsGameController(joystick_index);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDL_GameController that = (SDL_GameController) o;
        return ptr == that.ptr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ptr);
    }
}
