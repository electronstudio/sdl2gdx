package uk.co.electronstudio.sdl2gdx;

import com.badlogic.gdx.controllers.Controller;

public interface RumbleController extends Controller {
    boolean rumble(float leftMagnitude, float rightMagnitude, int duration_ms);
}
