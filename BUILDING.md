# Building

## Overview

1.  Clone the repo on Linux.  Run `./gradlew linuxNatives`
2.  The binaries for Windows are cross-compiled and so also need to be built on Linux (or MacOS).  Run `./gradlew windowsNatives`
3.  Clone the repo on a mac. Copy the files you just built (from the `libs` folder) to the mac.
4.  On the mac, run `./gradlew OSXNatives`
5.  Run `./gradlew uberjar` to generate a .jar file with all the dependencies bundled.

##  Linux build dependencies

The following packages (or equivalents) are needed:

```
ant
build-essential
sdl2-dev
```

If you've built C stuff for different platforms and bitnesses, you probably have all this stuff. If not, use your package manager to get them all. It should be something like this if you're on Ubuntu or Debian or whatever: 

```
sudo apt-get install ant build-essential sdl2-dev
```

sdl2-config must be in the path.

If your distro doesn't have an up to date version of SDL or you get errors, you can build it yourself from source:

```
make clean; ./configure CFLAGS="-fPIC -include src/force_link_glibc_2.5.h" CPPFLAGS="-fPIC -include src/force_link_glibc_2.5.h" --disable-audio --disable-render --disable-filesystem  --enable-hidapi 
make -j 8; sudo make install
```

The glibc header is to allow it work on older versions of glibc than the one currently installed.

## Windows (cross-compiled on Linux/MacOS) build dependencies

Linux:
```
sudo apt-get install ant mingw-w64
```
MacOS:
```
brew install ant mingw-w64  
```

You  need to install cross-compiled Windows 32 and 64 bit versions of SDL, e.g.

```
make clean; ./configure --host=i686-w64-mingw32 --disable-audio --disable-render --disable-filesystem  --enable-hidapi ; make -j 8; sudo make install
make clean; ./configure --host=x86_64-w64-mingw32 --disable-audio --disable-render --disable-filesystem  --enable-hidapi ; make -j 8; sudo make install
```

sdl2-config is assumed to be in /usr/local/cross-tools/ if it is not found there you will need to edit JamepadNativesBuild.java with the correct path.

## MacOS build dependencies

The MacOS binaries currently must be built on MacOS. (You can build the Windows ones here too with cross-compiler).  It is probably possible to build the Linux binaries also, but I haven't found a cross-compiler that works fully.

`brew install sdl2 ant`

or build SDL yourself:

```
make clean; ./configure --disable-audio --disable-render --disable-filesystem ; make -j 8 ; sudo make install

```

