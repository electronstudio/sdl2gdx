package com.badlogic.gdx.controllers.sdl2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeUtil {
    /*JNI

    #include "SDL.h"
    #include <vector>


    std::vector<int> ids;

    */

    public native void init(); /*

        const char *name, *type;
        int i;
        SDL_Joystick *joystick;

        SDL_Log("There are %d joysticks attached\n", SDL_NumJoysticks());
        for (i = 0; i < SDL_NumJoysticks(); ++i) {
            name = SDL_JoystickNameForIndex(i);
            SDL_Log("Joystick %d: %s\n", i, name ? name : "Unknown Joystick");
            joystick = SDL_JoystickOpen(i);
            if (joystick == NULL) {
                SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "SDL_JoystickOpen(%d) failed: %s\n", i,
                        SDL_GetError());
            }
//            else {
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
	*/

    /**
     * This method adds mappings held in the specified file. The file is copied to the temp folder so
     * that it can be read by the native code (if running from a .jar for instance)
     *
     * @param path The path to the file containing controller mappings.
     * @throws IOException if the file cannot be read, copied to a temp folder, or deleted.
     * @throws IllegalStateException if the mappings cannot be applied to SDL
     */
    public void addMappingsFromFile(String path) throws IOException, IllegalStateException {
        /*
        Copy the file to a temp folder. SDL can't read files held in .jars, and that's probably how
        most people would use this library.
         */
        Path extractedLoc = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir"), path);
        Files.copy(ClassLoader.getSystemResourceAsStream(path), extractedLoc,
                StandardCopyOption.REPLACE_EXISTING);

        if(!nativeAddMappingsFromFile(extractedLoc.toString())) {
            throw new IllegalStateException("Failed to set SDL controller mappings!");
        }

        Files.delete(extractedLoc);
    }
    private native boolean nativeAddMappingsFromFile(String path); /*
        if(SDL_GameControllerAddMappingsFromFile(path) < 0) {
            printf("NATIVE METHOD: Failed to load mappings from \"%s\"\n", path);
            printf("               %s\n", SDL_GetError());
            return JNI_FALSE;
        }

        return JNI_TRUE;
    */

    public native void update(); /*
        SDL_Event event;
        SDL_Joystick *joystick = NULL;
        SDL_JoystickID joystick_instance_id = -1;
        int device_index = -1;
        SDL_GameController* controller = NULL;

        while (SDL_PollEvent(&event)) {
            switch (event.type) {

            case SDL_JOYDEVICEADDED:
                device_index = event.jdevice.which;
                joystick = SDL_JoystickOpen(device_index);
                joystick_instance_id = SDL_JoystickInstanceID(joystick);
                SDL_Log("Joystick added device_index %d instance id %d", device_index, joystick_instance_id);
                SDL_Log("    VID/PID: 0x%.4x/0x%.4x\n", SDL_JoystickGetVendor(joystick), SDL_JoystickGetProduct(joystick));

//                if(SDL_IsGameController(device_index) == SDL_TRUE){
//                    SDL_Log("Controller");
//                    controller = SDL_GameControllerOpen(device_index);
//                    if(controller != NULL){
//
//                    }
//                }else{
//                    SDL_Log("Not controller");
//                }

                break;

            case SDL_JOYDEVICEREMOVED:
                joystick_instance_id = event.jdevice.which;
                SDL_Log("Joystick device %d removed.\n",  joystick_instance_id);
                break;

            case SDL_JOYAXISMOTION:
                SDL_Log("Joystick %d axis %d value: %d\n",
                       event.jaxis.which,
                       event.jaxis.axis, event.jaxis.value);
                break;
            case SDL_JOYHATMOTION:
                SDL_Log("Joystick %d hat %d value:",
                       event.jhat.which, event.jhat.hat);
                if (event.jhat.value == SDL_HAT_CENTERED)
                    SDL_Log(" centered");
                if (event.jhat.value & SDL_HAT_UP)
                    SDL_Log(" up");
                if (event.jhat.value & SDL_HAT_RIGHT)
                    SDL_Log(" right");
                if (event.jhat.value & SDL_HAT_DOWN)
                    SDL_Log(" down");
                if (event.jhat.value & SDL_HAT_LEFT)
                    SDL_Log(" left");
                SDL_Log("\n");
                break;
            case SDL_JOYBUTTONDOWN:
                SDL_Log("Joystick %d button %d down\n",
                       event.jbutton.which, event.jbutton.button);
                break;
            case SDL_JOYBUTTONUP:
                SDL_Log("Joystick %d button %d up\n",
                event.jbutton.which, event.jbutton.button);
                break;

            default:
                break;
            }
        }
*/
}
