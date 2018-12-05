# sdl2gdx (Java SDL & GDX Controllers)

Plug plug plug, this library was made for [RetroWar 8bit Party Battle](http://retrowar.net)!

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
* Can get more info, such as USB IDs and XInput Player LED number.
* Datab8ase of __mappings__ for large number of controllers, so you don't have to worry about it.
* SDL is recommended by Valve as second best way to do input for __Steam__ (after Steam Input of course!)
* Supports __Nintendo__ and __Sony__ controllers using USB drivers taken from Steam.

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
        compile "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
        compile "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
        compile "com.badlogicgames.gdx:gdx-controllers:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-desktop:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-platform:$gdxVersion:natives-desktop"
+       compile "uk.co.electronstudio.sdl2gdx:sdl2gdx:1.0.+"
    }
}
```

This will use SDL under the hood for all your desktop controllers.  That's it, done, with
no changes to your code!  See [LibGDX docs](https://github.com/libgdx/libgdx/wiki/Controllers) for how to use controllers.

### But but but

But what if you want to use a feature of SDL that is not supported by the LibGDX Controller API, e.g. rumble?  You can't, because LibGDX Controller doesn't support that.  See [ADVANCED](ADVANCED.md).

## Documentation

* [API docs](https://electronstudio.github.io/sdl2gdx/)
* [Hotplug CLI example](src/uk/co/electronstudio/sdl2gdx/tests/SDLHotplugTest.java)
* [GUI example](src/uk/co/electronstudio/sdl2gdx/tests/SDLTest.java)


## You might also like
* [Jamepad](https://github.com/williamahartman/Jamepad) - Java SDL Joystick library
* [RetroWar-common](https://github.com/electronstudio/retrowar-common) - LibGDX extension library
* [RetroWar](http://retrowar.net) - My game

## License

sdl2gdx is distributed under GPL+Classpath license, the same as OpenJDK itself, so you will have no
problem using it anywhere you use OpenJDK.

## Building from source

See [BUILDING](BUILDING.md).