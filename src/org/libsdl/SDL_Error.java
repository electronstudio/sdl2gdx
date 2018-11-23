package org.libsdl;

public class SDL_Error extends RuntimeException{
    public SDL_Error() {
        super(SDL.SDL_GetError());
    }
}
