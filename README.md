# Java SDL - GDX Controllers

Plug plug plug, this library was made for [RetroWar 8bit Party Battle](http://retrowar.net)!

## What

This library provides APIs at three layers:
1. A Java wrapper around SDL.  Currently we wrap most of Joystick and GameController.  PRs to wrap further APIs are welcome.  This wrapper is as close to the C source as
possible, so you should be able to port any SDL examples with no changes.
2. An OO wrapper on top of layer 1.  The same functions as provided by SDL, but with a class based API to make them more friendly to use.
3. An implementation of LibGDX Controller API on top of layer 2.  You can slot this straight in to any LibGDX app, or you can use it directly in a non-LibGDX app.

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

### LibGDX desktop project (the easy way)

```diff
buildscript{
    repositories {
+        jcenter()
    }
}
project(":desktop") {
    dependencies {
        compile "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
        compile "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
        compile "com.badlogicgames.gdx:gdx-controllers:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-desktop:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-platform:$gdxVersion:natives-desktop"
+       compile "uk.co.electronstudio.retrowar:sdl2gdx:1.0.+"
    }
}
```

But what if you want to use a feature not supported by the LibGDX API, e.g. rumble?

### LibGDX core project / non-LibGDX project

You can make your project depend on SDLJava.

```diff
project(":core") {
    dependencies {
+       compile "uk.co.electronstudio.retrowar:sdl2gdx:1.0.+"
    }
}
```

You can then create your own SDL2ControllerManager,
which is just like ControllerManager but with more features.  Works for LibGDX and non-LibGDX projects.
 This makes your project less portable, i.e. won't work on mobile.

```java
  SDL2ControllerManager controllerManager = new SDL2ControllerManager();
  SDL2Controller controller = (SDL2Controller)controllerManager.getControllers().get(0);
  controller.rumble(1.0f,1.0f,500);
```

### Reflection

Slower, unsafe, but avoids dependencies in your core project so your mobile projects still work.
```java
Method method = null;
Controller controller = Controllers.getControllers().get(0);
for( Method m: controller.getClass().getMethods()){
    if(m.getName().equals("rumble"))method = m;
}
try {
    method.invoke(controller, 1f, 1f, 500);
} catch (IllegalAccessException | InvocationTargetException e) {
    e.printStackTrace();
}    
```

## Documentation

* [API docs](https://electronstudio.github.io/sdl2gdx/)
* [Hotplug example](src/uk/co/electronstudio/sdl2gdx/tests/SDLHotplugTest.java)
* [GUI example](src/uk/co/electronstudio/sdl2gdx/tests/SDLTest.java)


## You might also like
* [Jamepad](https://github.com/williamahartman/Jamepad) - Java SDL Joystick library
* [RetroWar-common](https://github.com/electronstudio/retrowar-common) - LibGDX extension library
* [RetroWar](http://retrowar.net) - My game

## License

sdl2gdx is distributed under GPL+Classpath license, the same as OpenJDK itself, so you will have no
problem using it anywhere you use OpenJDK.

## Building from source

See [BUILDING](BUILDING.md)