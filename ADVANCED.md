# what if you want to use a feature of SDL that is not supported by the LibGDX Controller API, e.g. rumble?

#### 1 LibGDX core project

You can make your core subproject depend on sdl2gdx.

```diff
project(":core") {
    dependencies {
+           compile "uk.co.electronstudio.sdl2gdx:sdl2gdx:1.0.+"
    }
}
```

Then typecast to SDL2Controller when you need to access the SDL2 APIs.

```java
  SDL2Controller controller = (SDL2Controller)Controllers.getControllers().get(0);
  controller.rumble(1.0f,1.0f,500);
```

Unforunately a GDX core subproject
is not supposed to have native dependencies, so doing this will break other subprojects, e.g.
mobile.
                                                    

#### 2 Reflection

To avoid breaking things, you can add the dependency to your desktop subproject build, not core.  Then in your core project,
whenever you want to access an SDL feature, use reflection.

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

This is slower and unsafe, but avoids dependencies in your core project so your mobile projects still work.

#### 3 Wrapper

The 'proper' way to do it would be to write an interface around SDL2ControllerManager and then
provide separate jars, one with the SDL native implementation to be used on desktop and one with a noop implementation for mobile.  (Or an implementation that wraps GDXController.)

But then you aren't using the GDX Controller API anymore, you're using a similiar but
incompatible API.  If you don't care about being a slot-in replacement for GDX Controller, maybe you'd prefer to just [call SDL directly](https://electronstudio.github.io/sdl2gdx/org/libsdl/SDL.html)?

PRs and suggestions for this are welcome!