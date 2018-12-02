package uk.co.electronstudio.sdl2gdx.tests;

import com.badlogic.gdx.controllers.Controller;
import uk.co.electronstudio.sdl2gdx.SDL2Controller;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;
import org.libsdl.SDL_Error;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A quick and dirty interface to check if a controller is working. I hope you like swing!
 */
public class SDLTest {
    public static int NUM_CONTROLLERS = 4;

    public static void run() {
        JTabbedPane tabbedPane = new JTabbedPane();

        SDL2ControllerManager controllerManager = new SDL2ControllerManager();

        rumbleExample(controllerManager);

        reflectionExample(controllerManager);

        JFrame testFrame = new JFrame();
        SDLInfoPanel[] controllerTabs = setup(tabbedPane, testFrame);

        while (true) {
            mainLoop(controllerManager, testFrame, controllerTabs);
        }
    }

    private static void mainLoop(SDL2ControllerManager controllerManager, JFrame testFrame, SDLInfoPanel[] controllerTabs) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controllerManager.pollState();
        } catch (SDL_Error sdl_error) {
            sdl_error.printStackTrace();
        }
        for (int i = 0; i < controllerManager.getControllers().size; i++) {
            Controller controllerAtIndex = controllerManager.getControllers().get(i);
            controllerTabs[i].updatePanel((SDL2Controller) controllerAtIndex);
        }
        testFrame.repaint();
    }

    private static SDLInfoPanel[] setup(JTabbedPane tabbedPane, JFrame testFrame) {
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        testFrame.setLocationRelativeTo(null);
        testFrame.setMinimumSize(new Dimension(640, 350));
        testFrame.setResizable(true);
        testFrame.setVisible(true);

        SDLInfoPanel[] controllerTabs = new SDLInfoPanel[NUM_CONTROLLERS];
        for (int i = 0; i < NUM_CONTROLLERS; i++) {
            controllerTabs[i] = new SDLInfoPanel();
            tabbedPane.add("   Controller " + (i + 1) + "   ", controllerTabs[i]);

        }
        testFrame.setContentPane(tabbedPane);
        return controllerTabs;
    }

    private static void rumbleExample(SDL2ControllerManager controllerManager) {
        SDL2Controller controller = (SDL2Controller) controllerManager.getControllers().get(0);
        controller.rumble(1.0f, 1.0f, 500);
    }

    private static void reflectionExample(SDL2ControllerManager controllerManager) {
        Method method = null;
        Controller controller = controllerManager.getControllers().get(0);
        for (Method m : controller.getClass().getMethods()) {
            if (m.getName().equals("rumble")) method = m;
        }
        try {
            method.invoke(controller, 1f, 1f, 500);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        run();
    }
}
