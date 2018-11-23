package org.libsdl;

import java.util.Objects;

public final class SDL_JoystickID {
    final int id;
    SDL_JoystickID(int id) { this.id = id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDL_JoystickID that = (SDL_JoystickID) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
