## Building from source 
1.  run `./gradlew windowsNatives`
2.  run `./gradlew linuxNatives`
3.  Clone the repo on a mac. Copy the files you just built (from the `libs` folder) to the mac 
4.  On the mac, run `./gradlew OSXNatives`
5.  run `./gradle dist` to generate a .jar file with all the dependencies bundled

### Dependencies for Building on Linux
Right now the Windows and Linux binaries, sdl2gdx needs to be built on Linux. The binaries for Windows are cross-compiled.

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


### Dependencies for Building on OS X
The OS X binaries currently must be built on OS X. It is probably possible to build the Windows and Linux binaries here too, but I haven't tried that out.

The dependencies are pretty much the same (gradle, ant, g++). These packages can be installed from homebrew.
