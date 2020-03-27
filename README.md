# Advert

[RetroWar: 8-bit Party Battle](https://store.steampowered.com/app/664240/RetroWar_8bit_Party_Battle/?git) is out now.  Defeat up to 15 of your friends in a tournament of 80s-inspired retro mini games.

# sdl2gdx (Java SDL & GDX Controllers)

## What

This library provides APIs at three layers:
1. [A Java wrapper around SDL](https://electronstudio.github.io/sdl2gdx/org/libsdl/SDL.html).  Currently we wrap most of Joystick and GameController.  PRs to wrap further APIs are welcome.  This wrapper is as close to the C source as
possible, so you should be able to port any SDL examples with no changes.
2. [An OO wrapper](https://electronstudio.github.io/sdl2gdx/org/libsdl/SDL_Joystick.html) on top of layer 1.  The same functions as provided by SDL, but with a class based API to make them more friendly to use.
3. [An implementation of LibGDX Controller API](https://electronstudio.github.io/sdl2gdx/uk/co/electronstudio/sdl2gdx/SDL2Controller.html) on top of layer 2.  You can slot this straight in to any LibGDX app, or you can use it directly in a non-LibGDX app.

Thanks to [Jamepad](https://github.com/williamahartman/Jamepad) by William Harman for providing the basis, native build system and inspiration for this project.

## Why

Compared to the default LibGDX Controller implementation:
* __Hotplug__ works.
* Doesn't quit working when the screenmode changes.
* __Rumble__!
* Can get more info, such as device power level and XInput Player LED number.
* Database of __mappings__ for large number of controllers, so you don't have to worry about it.
* SDL is recommended by Valve as second best way to do input for __Steam__ (after Steam Input of course!)
* Supports __Nintendo__ and __Sony__ controllers using USB drivers taken from Steam.
* Supports more than 4 XInput controllers

## How

Add the repo if you don't have it in your build.gradle already

```diff
buildscript{
    repositories {
+        jcenter()
    }
```

### For a project not using LibGDX

```diff
    dependencies {
+       compile "uk.co.electronstudio.sdl2gdx:sdl2gdx:1.0.+"
    }
}
```

See examples and docs below for how to call the API.


### For a LibGDX desktop project

```diff
project(":desktop") {
    dependencies {
        implementation "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
        implementation "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
        implementation "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop"
-       implementation "com.badlogicgames.gdx:gdx-controllers-desktop:$gdxVersion"
-       implementation "com.badlogicgames.gdx:gdx-controllers-platform:$gdxVersion:natives-desktop"
    }
}

project(":core") {
    dependencies {
        implementation "com.badlogicgames.gdx:gdx:$gdxVersion"
        implementation "com.badlogicgames.gdx:gdx-box2d:$gdxVersion"
        implementation "com.badlogicgames.gdx:gdx-controllers:$gdxVersion"
+       implementation "uk.co.electronstudio.sdl2gdx:sdl2gdx:1.0.4"
    }
}

```

This will use SDL under the hood for all your controllers.  That's it, done, with
no changes to your code!  See [LibGDX docs](https://github.com/libgdx/libgdx/wiki/Controllers) for how to use controllers.

Adding to the core project means you are free to use the additional SDL2 APIs anywhere in your code, but it won't work if
you also have html5 or mobile projects.  In that case add sdl2gdx to just the desktop project and not the core project.

[Example LibGDX project](https://github.com/electronstudio/sdl2gdx-test)

### Rumble

What if you want to use a feature of SDL that is not supported by the LibGDX Controller API, e.g. rumble?

If you didn't add sdl2gdx to your core project, you will need instead to create a file `RumbleController.java` in your core project:

```
package uk.co.electronstudio.sdl2gdx;
import com.badlogic.gdx.controllers.Controller;

public interface RumbleController extends Controller {
    boolean rumble(float leftMagnitude, float rightMagnitude, int duration_ms);
}

```

Then when you want to use rumble, make sure you're on Desktop platform and typecast:

```
if(Gdx.app.getType() == Application.ApplicationType.Desktop){
    RumbleController controller = (RumbleController) Controllers.getControllers().get(0);
    controller.rumble(1.0f,1.0f,500);
}
```

You could also typecast your Controller to SDL2Controller for other features like power level.

## Documentation

* [API docs](https://electronstudio.github.io/sdl2gdx/)
* [Hotplug CLI example](src/uk/co/electronstudio/sdl2gdx/tests/SDLHotplugTest.java)
* [GUI example](src/uk/co/electronstudio/sdl2gdx/tests/SDLTest.java)


## You might also like
* [Jamepad](https://github.com/williamahartman/Jamepad) - Alternate API for accessing sdl2gdx
* [RetroWar-common](https://github.com/electronstudio/retrowar-common) - LibGDX extension library
* [RetroWar](http://retrowar.net) - My game

## License

sdl2gdx is distributed under GPL+Classpath license, the same as OpenJDK itself, so you will have no
problem using it anywhere you use OpenJDK.

## Building from source

See [BUILDING](BUILDING.md).
