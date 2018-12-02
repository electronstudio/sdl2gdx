package org.libsdl;

public class SDL_Error extends Exception{
    public SDL_Error() {
        super(SDL.SDL_GetError());
    }
}
