# Java SDL - GDX Controllers

## What

This library provides APIs at three layers:
1. A Java wrapper around SDL.  Currently we wrap most of Joystick and GameController.  PRs to wrap further APIs are welcome.  This wrapper is as close to the C source as
possible, so you should be able to port any SDL examples with no changes.
2. An OO wrapper on top of layer 1.  The same functions as provided by SDL, but with a class based API to make them more friendly to use.
3. An implementation of LibGDX Controller API on top of layer 2.  You can slot this straight in to any LibGDX app, or you can use it directly in a non-LibGDX app.

Thanks to [Jamepad](https://github.com/williamahartman/Jamepad) by William Harman for providing the build system and inspiring this project.

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
project(":desktop") {
    dependencies {
        compile "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
        compile "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
         compile "com.badlogicgames.gdx:gdx-controllers:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-desktop:$gdxVersion"
-       compile "com.badlogicgames.gdx:gdx-controllers-platform:$gdxVersion:natives-desktop"
+       compile "uk.co.electronstudio.retrowar:dsfdsf:1.0.+"
    }
}
```

But what if you want to use a feature not supported by the LibGDX API, e.g. rumble?

### LibGDX core project / non-LibGDX project

You can make your project depend on SDLJava.

```diff
project(":core") {
    dependencies {
+       compile "uk.co.electronstudio.retrowar:dsfdsf:1.0.+"
    }
}
```

This makes your project less portable.  But you can then create your own SDL2ControllerManager,
which is just like ControllerManager but with more features.  Works for LibGDX and non-LibGDX projects.

```java
  SDL2ControllerManager controllerManager = new SDL2ControllerManager();
  SDL2Controller controller = (SDL2Controller)controllerManager.getControllers().get(0);
  controller.rumble(1.0f,1.0f,500);
```

### Reflection

Slower, but avoids dependencies.
```java
    SDL2ControllerManager controllerManager = new SDL2ControllerManager();
    Method method = null;
    Controller controller = controllerManager.getControllers().get(0);
    for( Method m: controller.getClass().getMethods()){
        if(m.getName().equals("rumble"))method = m;
    }
    try {
        method.invoke(controller, 1f, 1f, 500);
    } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
    }
```


current Linux compile flags to avoid dependencies:

./configure CFLAGS=-fPIC CPPFLAGS=-fPIC --disable-audio --disable-ime --disable-video-vulkan
 


# Building
1.  run `./gradlew windowsNatives`
2.  run `./gradlew linuxNatives`
3.  Clone the repo on a mac. Copy the files you just built (from the `libs` folder) to the mac 
4.  On the mac, run `./gradlew OSXNatives`
5.  run `./gradle dist` to generate a .jar file with all the dependencies bundled

## Dependencies for Building on Linux
Right now the Windows and Linux binaries, Jamepad needs to be built on Linux. The binaries for Windows are cross-compiled.

The following packages (or equivalents) are needed:

```
ant
build-essential 
mingw-w64
```

If you've built C stuff for different platforms and bitnesses, you probably have all this stuff. If not, use your package manager to get them all. It should be something like this if you're on Ubuntu or Debian or whatever: 

```
sudo apt-get install ant build-essential mingw-w64
```

You also need to install cross compiled 32 and 64 bit versions of SDL, e.g.

```
./configure --host=i686-w64-mingw32 ; make ; sudo make install
./configure --host=x86_64-w64-mingw32 ; make ; sudo make install
```


## Dependencies for Building on OS X
The OS X binaries currently must be built on OS X. It is probably possible to build the Windows and Linux binaries here too, but I haven't tried that out.

The dependencies are pretty much the same (gradle, ant, g++). These packages can be installed from homebrew.
