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

### LibGDX project, simple way

```diff
project(":desktop") {
    dependencies {
        compile "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
        compile "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
-       compile "com.badlogicgames.gdx:gdx-controllers-desktop:$gdxVersion"~
-       compile "com.badlogicgames.gdx:gdx-controllers-platform:$gdxVersion:natives-desktop"~~
+       compile "uk.co.electronstudio.retrowar::1.0.+"
    }
}
```


current Linux compile flags to avoid dependencies:

./configure CFLAGS=-fPIC CPPFLAGS=-fPIC --disable-audio --disable-ime --disable-video-vulkan
 

#### New changes in this fork

* Uses new rumble API and depreciates the old haptics API.
* SDL 2.0.9 (or greater) dev libraries must be installed on the system.
* SDL must have been compiled with "./configure CFLAGS=-fPIC CPPFLAGS=-fPIC" (on Linux at least)
* sdl2-config must be in the path.
* If you want you could compile your own SDL without video and install it into a local directory, then make some minor changes to JamepadNativesBuild.java to tell it where to find your SDL.  However we no longer attempt to do this automatically for you.
* Creating portable binaries for Linux is a minefield at the best of times so we need to do some testing on different Linux distros to make sure they work.


#### A better way to use gamepads in Java

Jamepad is a library for using gamepads in Java. It's based on SDL2 ([here](https://www.libsdl.org/)) and uses jnigen ([more info here](https://github.com/libgdx/libgdx/wiki/jnigen)). We also use [this](https://github.com/gabomdq/SDL_GameControllerDB) really nice database of gamepad mappings.

Other gamepad libraries are missing stuff developers need. For most libraries, Xbox 360 controllers on windows are not properly supported. The libraries that do support Xbox 360 controllers are not cross platform. On some, hotplugging controllers is not supported.

Jamepad has:
  - One library that supports all platforms (Windows, OSX, and Linux)
  - XInput support on Windows for full Xbox 360 controller support.
  - Support for plugging/unplugging controllers at runtime.
  - Support for rumble
  - Button/Axis mappings for popular controllers.
  - A permissive license. You can include this use this library in proprietary projects without sharing source.

#### Stuff You Should Know About Jamepad

- On Windows (only 7 and up were tested), no special dependencies are needed.
- On Linux, runtime dependencies are:
  - libevdev
  - libudev
- On OS X, no special dependencies are needed
  - If you want to use Xbox controllers, you need separate drivers for them. The ones [here](https://github.com/360Controller/360Controller) have been tested with Jamepad and work properly.
  
#### Current Limitations
- The order of gamepads on Windows is not necessarily the order they were plugged in. XInput controllers will always appear before DirectInput controllers, regardless of when they were plugged in. This means that the player numbers associated with each controller can change unexpectedly if XInput controllers are plugged in or disconnected while DirectInput controllers are present.
- If using getState() in ControllerManager, a new ControllerState is instantiated on each call. For some games, this could pose a problem.
- For now, when we build SDL, the  dynamic API stuff is disabled. This seems bad and should probably change. I just don't know how to get it to work through JNI with that stuff enabled.
  
## Using Jamepad

#### Getting Jamepad

No Mavern repo yet.

Ddownload the .jar file from the releases section and add it to your project as usual.

#### Using Jamepad
There are two main ways to use Jamepad. Both rely on a ControllerManager Object.

```java
ControllerManager controllers = new ControllerManager();
controllers.initSDLGamepad();
```

For most applications, using the getState() method in ControllerManager is best. This method returns an immutable ControllerState object that describes the state of the controller at the instant the method is called. Using this method, you don't need to litter code with a bunch of exceoption handling or handle the possiblity of controller disconnections at weird times. 

If a controller is disconnected, the returned ControllerState object has the isConnected field set to false. All other fields are either false (for buttons) or 0 (for axes).

Here's a simple example:

```java
//Print a message when the "A" button is pressed. Exit if the "B" button is pressed 
//or the controller disconnects.
while(true) {
  ControllerState currState = controllers.getState(0);
  
  if(!currState.isConnected || currState.b) {
    break;
  }
  if(currState.a) {
    System.out.println("\"A\" on \"" + currState.controllerType + "\" is pressed");
  }
}
```

For a select few applications, getState() might not be the best decision. Since ControllerState is immutable, a new one is instantiated on each call to getState(). This should be fine for normal desktop JVMs; both Oracle's JVM and the OpenJDK one should absolutely be able to handle this. What problems do come up could probably be solved with some GC tuning.

If these allocations do end up being an actual problem, you can access the internal representation of the controllers. This is more complicated to use, and you might need to deal with some exceptions.

Here's a pretty barebones example:

```java
//Print a message when the "A" button is pressed. Exit if the "B" button is pressed 
//or the controller disconnects.
ControllerIndex currController = controllers.getControllerIndex(0);

while(true) {
  controllers.update(); //If using ControllerIndex, you should call update() to check if a new controller
                        //was plugged in or unplugged at this index.
  try {
    if(currController.isButtonPressed(ControllerButton.A)) {
      System.out.println("\"A\" on \"" + currController.getName() + "\" is pressed");
    }
    if(currController.isButtonPressed(ControllerButton.B)) {
      break;
    }
  } catch (ControllerUnpluggedException e) {   
    break;
  }
}
```

When you're finished with your gamepad stuff, you should call quitSDLGamepad() to free the native library.

```java
controllers.quitSDLGamepad();
```

## Building Jamepad
1.  run `./gradlew windowsNatives`
2.  run `./gradlew linuxNatives`
3.  Clone the repo on a mac. Copy the files you just built (from the `libs` folder) to the mac 
4.  On the mac, run `./gradlew OSXNatives`
5.  run `./gradle dist` to generate a .jar file with all the dependencies bundled

#### Dependencies for Building Jamepad on Linux
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


#### Dependencies for Building Jamepad on OS X
The OS X binaries currently must be built on OS X. It is probably possible to build the Windows and Linux binaries here too, but I haven't tried that out.

The dependencies are pretty much the same (gradle, ant, g++). These packages can be installed from homebrew.
