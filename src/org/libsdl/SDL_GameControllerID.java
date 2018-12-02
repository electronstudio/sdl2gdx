package org.libsdl;

import java.util.Objects;

public class SDL_GameControllerID {
    final int id;
    SDL_GameControllerID(int id) { this.id = id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDL_GameControllerID that = (SDL_GameControllerID) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
